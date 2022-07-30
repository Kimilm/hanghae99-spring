package com.sparta.selectshop.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
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
