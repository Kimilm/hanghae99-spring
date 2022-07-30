package com.sparta.selectshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
        httpSecurity.csrf().ignoringAntMatchers("/user/**");

        httpSecurity.authorizeHttpRequests(authz -> authz
                        // image 폴더를 login 없이 허용
                        .antMatchers("/images/**").permitAll()
                        // css 폴더를 login 없이 허용
                        .antMatchers("/css/**").permitAll()
                        // 어떤 요청이든 '인증'
                        .anyRequest().authenticated())
                // 로그인 기능 허용
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/selectshop")
                .failureUrl("/user/login?error")
                .permitAll()
                // 로그아웃 기능 허용
                .and()
                .logout()
                .permitAll();

        return httpSecurity.build();
    }
}
