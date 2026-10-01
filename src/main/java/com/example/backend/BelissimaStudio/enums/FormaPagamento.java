package com.example.backend.BelissimaStudio.enums;

public enum FormaPagamento {
    PIX("Pix"),
    CARTAO_DEBITO("Debito"),
    CARTAO_CREDITO("Credito"),
    DINHEIRO("Dinheiro");

    private final String nome;

    FormaPagamento(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
