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
        httpSecurity.authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
                .formLogin()
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .permitAll();

        return httpSecurity.build();
    }
}
