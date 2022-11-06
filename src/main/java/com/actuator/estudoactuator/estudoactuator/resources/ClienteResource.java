package com.actuator.estudoactuator.estudoactuator.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("cliente")
public class ClienteResource {

    @GetMapping
    public ResponseEntity<Cliente> cliente() {
        return ResponseEntity.ok(new Cliente("Roger", "Assis", BigDecimal.valueOf(1550.10D)));
    }
}
