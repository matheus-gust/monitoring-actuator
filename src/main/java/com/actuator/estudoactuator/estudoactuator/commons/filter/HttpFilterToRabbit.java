package com.actuator.estudoactuator.estudoactuator.commons.filter;

import com.actuator.estudoactuator.estudoactuator.commons.rabbit.QueueSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class HttpFilterToRabbit extends OncePerRequestFilter {

    private final QueueSender queueSender;

    public HttpFilterToRabbit(QueueSender queueSender) {
        this.queueSender = queueSender;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MultiReadRequest wrapper = new MultiReadRequest((HttpServletRequest) request);
        filterChain.doFilter(wrapper, response);

        ClientMessage clientMessage = new ClientMessage(UUID.fromString("091d2604-65ce-11ed-9022-0242ac120002"), "teste", request.getLocalAddr(), request.getLocalPort());
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage(response.getStatus(), wrapper.requestBody, request.getMethod(), request.getRemoteAddr());

        RabbitMessage message = new RabbitMessage(clientMessage, httpRequestMessage);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(message);
        queueSender.send(json);
    }

    class MultiReadRequest extends HttpServletRequestWrapper {

        private String requestBody;

        @SneakyThrows
        public MultiReadRequest(HttpServletRequest request) {
            super(request);
            requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody.getBytes());
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }

        @Override
        @SneakyThrows
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(this.getInputStream(), StandardCharsets.UTF_8));
        }
    }
}
