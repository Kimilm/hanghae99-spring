package com.sparta.selectshop.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    // 암호화 알고리즘 빈 등록
    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
                .ignoringAntMatchers("/user/**")
                // products 요청 API CSRF 무시
                .ignoringAntMatchers("/api/products/**");


        httpSecurity.authorizeHttpRequests(authz -> authz
                        // image 폴더를 login 없이 허용
                        .antMatchers("/images/**").permitAll()
                        // css 폴더를 login 없이 허용
                        .antMatchers("/css/**").permitAll()
                        // 회원 관리 처리 API 모두 login 없이 허용
                        .antMatchers("/user/**").permitAll()
                        // 어떤 요청이든 '인증'
                        .anyRequest().authenticated())
                // 로그인 기능 허용
                .formLogin()
                .loginPage("/user/login")   // GET
                .loginProcessingUrl("/user/login")  // POST
                .defaultSuccessUrl("/selectshop")
                .failureUrl("/user/login?error")
                .permitAll()
                // 로그아웃 기능 허용
                .and()
                .logout()
                .logoutUrl("/user/logout")  // GET
                .permitAll()
                // 접근 불가 페이지 설정
                .and()
                .exceptionHandling()
                .accessDeniedPage("/forbidden.html");

        return httpSecurity.build();
    }
}
