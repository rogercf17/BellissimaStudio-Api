package com.example.backend.BelissimaStudio.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank(message = "Nome da cliente é obrigatório")
        String nome,

        @NotBlank(message = "Telefone da cliente é obrigatório")
        String telefone
) { }
