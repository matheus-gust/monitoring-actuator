package com.actuator.estudoactuator.estudoactuator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DatabaseHealth implements HealthIndicator {

    private final String DATABASE_SERVICE = "Database Service";

    @Autowired
    JdbcTemplate template;

    Map<String, String> datails = new HashMap(Map.of(
            "Database", "MySql",
            "Version", "8.0.3"
    ));

    @Override
    public Health health() {
       if(isDatabaseUp()) {
           this.datails.put(DATABASE_SERVICE, "Banco de dados Disponível");
           return Health.up().withDetails(this.datails).build();
       }
       this.datails.put(DATABASE_SERVICE, "Banco de dados indisponível");
       return Health.down().withDetails(this.datails).build();
    }

    private boolean isDatabaseUp() {
        List<Object> results = template.query("select 1 from dual",
                new SingleColumnRowMapper<>());
        return (results.size() != 1) ? false : true;
    }
}
