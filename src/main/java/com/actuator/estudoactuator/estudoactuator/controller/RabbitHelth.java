package com.actuator.estudoactuator.estudoactuator.controller;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.Channel;

import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class RabbitHelth implements HealthIndicator {

    Map<String, String> datails = new HashMap(Map.of(
            "Type", "RabbitMQ"
    ));

    private final RabbitTemplate rabbitTemplate;

    public RabbitHelth(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Health health() {
        datails.put("version", getVersion());
        return Health.up().withDetails(datails).build();
    }


    private String getVersion() {
        return this.rabbitTemplate.execute(new ChannelCallback<String>() {
            @Override
            public String doInRabbit(Channel channel) throws Exception {
                Map<String, Object> serverProperties = channel.getConnection()
                        .getServerProperties();
                return serverProperties.get("version").toString();
            }
        });
    }

}