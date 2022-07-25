package com.sparta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling   // 스프링 부트 스케쥴러 작동
@EnableJpaAuditing  // 스프링 부트 JPA 시간 자동 변경
public class Config {
}
