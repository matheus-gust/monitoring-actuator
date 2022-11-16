package com.actuator.estudoactuator.estudoactuator.commons.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HttpRequestMessage {
    private Integer status;
    private String body;
    private String method;
    private String sender;
    private String requestedUrl;
    private String message;
    private String requestDateTime;
}
