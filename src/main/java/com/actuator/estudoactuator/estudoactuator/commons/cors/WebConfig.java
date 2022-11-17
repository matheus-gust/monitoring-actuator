package com.actuator.estudoactuator.estudoactuator.commons.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/actuator/**")
                .allowedOrigins("http://127.0.0.1")
                .allowedMethods("PUT", "DELETE", "GET")
                .allowedHeaders("*")
                .allowCredentials(false).maxAge(3600);
    }
}