package com.tam.threeam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //운영 환경에 배포할 경우에는 주석 처리
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowCredentials(true)
                .allowedOrigins("http://localhost:3000","http://localhost:8080")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
