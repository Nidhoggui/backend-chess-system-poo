package com.backendchesssystempoo.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig{
    
    @Bean
    public WebMvcConfigurer corsConfigure() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*")
                    .allowedOrigins("http://localhost:8080");
            }
        };
    }
}