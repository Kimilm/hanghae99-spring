package com.sparta.selectshop.aop;

import com.sparta.selectshop.models.ApiUseTime;
import com.sparta.selectshop.models.user.User;
import com.sparta.selectshop.repository.ApiUseTimeRepository;
import com.sparta.selectshop.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UseTimeAop {

    private final ApiUseTimeRepository apiUseTimeRepository;

    @Around("execution(public * com.sparta.selectshop.controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object output = joinPoint.proceed();
            return output;
        } finally {
            long endTime = System.currentTimeMillis();

            long runTime = endTime - startTime;

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // 로그인 회원이 없는 경우, 수행시간 기록 x
            if (auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class) {
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
                User loginUser = userDetails.getUser();

                ApiUseTime apiUseTime = apiUseTimeRepository.findByUser(loginUser).orElse(null);

                if(apiUseTime == null) {
                    // 로그인 회원의 기록이 없다면
                    apiUseTime = new ApiUseTime(loginUser, runTime);
                } else {
                    apiUseTime.addUseTime(runTime);
                }

                log.info("[Api Use Time] username: " + loginUser.getUsername());
                apiUseTimeRepository.save(apiUseTime);
            }
        }
    }
}
