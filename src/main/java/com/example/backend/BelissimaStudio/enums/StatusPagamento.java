package com.example.backend.BelissimaStudio.enums;

public enum StatusPagamento {
    PAGO("Pago"),
    PENDENTE("Pendente");

    private final String nome;

    StatusPagamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
