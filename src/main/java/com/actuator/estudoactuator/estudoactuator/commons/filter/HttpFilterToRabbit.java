package com.actuator.estudoactuator.estudoactuator.commons.filter;

import com.actuator.estudoactuator.estudoactuator.commons.rabbit.QueueSender;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class HttpFilterToRabbit implements Filter {

    private final QueueSender queueSender;

    public HttpFilterToRabbit(QueueSender queueSender) {
        this.queueSender = queueSender;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        queueSender.send("teste");
        chain.doFilter(request, response);
    }
}
