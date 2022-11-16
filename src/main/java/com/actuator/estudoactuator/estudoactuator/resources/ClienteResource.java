package com.actuator.estudoactuator.estudoactuator.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("cliente")
public class ClienteResource {

    @GetMapping
    public ResponseEntity<Cliente> buscaCliente() {
        return ResponseEntity.ok(new Cliente("Roger", "Assis", BigDecimal.valueOf(1550.10D)));
    }

    @PostMapping
    public ResponseEntity<Cliente> insereCliente(@Valid @RequestBody Cliente cliente) {
        Cliente client = cliente;
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }
}
