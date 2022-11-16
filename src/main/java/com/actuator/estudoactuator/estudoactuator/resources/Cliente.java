package com.actuator.estudoactuator.estudoactuator.resources;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class Cliente {

    @NotNull
    private String nome;
    @NotNull
    private String endereco;
    @NotNull
    private BigDecimal saldo;

    public Cliente() {}

    public Cliente(String nome, String endereco, BigDecimal saldo) {
        this.nome = nome;
        this.endereco = endereco;
        this.saldo = saldo;
    }
}
