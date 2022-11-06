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
            "Database", "Postgres",
            "Version", "15.0.0"
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
        List<Object> results = template.query("SELECT datname FROM pg_catalog.pg_database WHERE oid = 16388",
                new SingleColumnRowMapper<>());
        return (results.size() > 0) ? true : false;
    }
}
