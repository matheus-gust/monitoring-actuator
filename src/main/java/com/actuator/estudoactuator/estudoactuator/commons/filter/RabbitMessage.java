package com.actuator.estudoactuator.estudoactuator.commons.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RabbitMessage {
    private ClientMessage clientMessage;
    private HttpRequestMessage httpRequestMessage;
}
