package com.actuator.estudoactuator.estudoactuator.commons.config;

import com.actuator.estudoactuator.estudoactuator.commons.filter.HttpFilterToRabbit;
import com.actuator.estudoactuator.estudoactuator.commons.rabbit.QueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpFilterConfig {

    @Autowired
    private QueueSender queueSender;

    @Bean
    public FilterRegistrationBean<HttpFilterToRabbit> httpFilter(){
        FilterRegistrationBean<HttpFilterToRabbit> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new HttpFilterToRabbit(queueSender));
        registrationBean.addUrlPatterns("/cliente/*");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}
