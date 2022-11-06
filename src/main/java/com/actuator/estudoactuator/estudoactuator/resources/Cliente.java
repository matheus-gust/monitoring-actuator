package com.actuator.estudoactuator.estudoactuator.resources;

import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class Cliente {

    private String nome;
    private String endereco;
    private BigDecimal saldo;

    public Cliente() {}

    public Cliente(String nome, String endereco, BigDecimal saldo) {
        this.nome = nome;
        this.endereco = endereco;
        this.saldo = saldo;
    }
}
