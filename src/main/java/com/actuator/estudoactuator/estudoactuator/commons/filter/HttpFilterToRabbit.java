package com.actuator.estudoactuator.estudoactuator.commons.filter;

import com.actuator.estudoactuator.estudoactuator.commons.rabbit.QueueSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
        MultiReadResponse responseWrapper = new MultiReadResponse((HttpServletResponse) response);

        try {
            filterChain.doFilter(wrapper, responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClientMessage clientMessage = new ClientMessage(UUID.fromString("091d2604-65ce-11ed-9022-0242ac120002"), "teste", request.getLocalAddr(), request.getLocalPort());
        HttpRequestMessage httpRequestMessage = new HttpRequestMessage(response.getStatus(), wrapper.getRequestBody(), request.getMethod(), request.getRemoteAddr(), request.getRequestURL().toString(), responseWrapper.getResponseBody(), LocalDateTime.now().toString());

        RabbitMessage message = new RabbitMessage(clientMessage, httpRequestMessage);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(message);
        queueSender.send(json);
    }

    class MultiReadResponse extends ContentCachingResponseWrapper {

        public MultiReadResponse(HttpServletResponse response) {
            super(response);
        }

        public String getResponseBody() throws IOException {
            byte[] responseArray = this.getContentAsByteArray();
            String responseBody = new String(responseArray, this.getCharacterEncoding());
            this.copyBodyToResponse();
            return responseBody;
        }
    }

    class MultiReadRequest extends HttpServletRequestWrapper {

        private String requestBody;

        @SneakyThrows
        public MultiReadRequest(HttpServletRequest request) {
            super(request);
            requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        public String getRequestBody() {
            return requestBody;
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
