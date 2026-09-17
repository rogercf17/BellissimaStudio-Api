package com.example.backend.BelissimaStudio.model;

public enum Servico {
    ESCOVA("Escova"),
    LUZES("Luzes"),
    PROGRESSIVA("Progressiva"),
    SOMBRANCELHA("Sobrancelha"),
    UNHA("Unha"),
    TERAPIA_CAPILAR("Terapia Capilar"),
    BOTOX("Botox");

    private final String nome;

    Servico(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
