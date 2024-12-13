package com.example.loascheduler.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 요청에 대해 CORS 허용
        registry.addMapping("/**")
                .allowedOrigins("https://jhwook.github.io", "https://loa-scheduler.com", "https://www.loa-scheduler.com", "http://localhost:3000") // 클라이언트의 URL을 지정
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 허용할 헤더
                .allowCredentials(true); // 쿠키 포함 허용
    }
}