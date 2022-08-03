package com.sparta.selectshop.security.config;

import com.sparta.selectshop.security.FilterSkipMatcher;
import com.sparta.selectshop.security.FormLoginSuccessHandler;
import com.sparta.selectshop.security.filter.FormLoginFilter;
import com.sparta.selectshop.security.filter.JwtAuthFilter;
import com.sparta.selectshop.security.jwt.HeaderTokenExtractor;
import com.sparta.selectshop.security.provider.FormLoginAuthProvider;
import com.sparta.selectshop.security.provider.JWTAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTAuthProvider jwtAuthProvider;
    private final HeaderTokenExtractor headerTokenExtractor;
    private final UserDetailsService userDetailsService;
    private final AuthenticationConfiguration configuration;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager());
        formLoginFilter.setFilterProcessesUrl("/user/login");
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler();
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {
        return new FormLoginAuthProvider(encodePassword());
    }

    @Bean
    public JwtAuthFilter jwtFilter() throws Exception {

        List<String> skipPathList = new ArrayList<>();

        // Static 정보 접근 허용
        skipPathList.add("GET,/images/**");
        skipPathList.add("GET,/css/**");

        // h2-console 허용
        skipPathList.add("GET,/h2-console/**");
        skipPathList.add("POST,/h2-console/**");

        // 회원 관리 API 허용
        skipPathList.add("GET,/user/**");
        skipPathList.add("POST,/user/signup");

        // 작성글 조회 허용
        skipPathList.add("GET,/expert/api/**");

        skipPathList.add("GET,/");
        skipPathList.add("GET,/basic.js");

        skipPathList.add("GET,/favicon.ico");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPathList,
                "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(authenticationManager());

        return filter;
    }

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
                .ignoringAntMatchers("/api/products/**")
                // folder 요청 API CSRF 무시
                .ignoringAntMatchers("/api/folders/**");


        httpSecurity.authorizeHttpRequests(authz -> authz
                        // image 폴더를 login 없이 허용
                        .antMatchers("/images/**").permitAll()
                        // css 폴더를 login 없이 허용
                        .antMatchers("/css/**").permitAll()
                        // 회원 관리 처리 API 모두 login 없이 허용
                        .antMatchers("/user/**").permitAll()
                        // 어떤 요청이든 '인증'
                        .anyRequest().authenticated())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 필터 추가
                .and()
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
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
                .accessDeniedPage("/forbidden.html")
                // 프로바이더 추가
                .and()
                .getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(formLoginAuthProvider())
                .authenticationProvider(jwtAuthProvider);

        return httpSecurity.build();
    }
}
