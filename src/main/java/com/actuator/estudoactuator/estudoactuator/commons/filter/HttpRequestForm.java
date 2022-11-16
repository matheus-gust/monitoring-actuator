package com.actuator.estudoactuator.estudoactuator.commons.filter;

import lombok.Data;

import java.util.UUID;

@Data
public class HttpRequestForm {

    private Integer status;
    private String body;
    private String sender;
    private UUID clientId;
    private String clientDescrption;
}
