package com.example.backend.BelissimaStudio.dto;

import com.example.backend.BelissimaStudio.enums.FormaPagamento;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RegistrarPagamentoDTO(
        @NotNull(message = "Forma de pagamento é obrigatória")
        FormaPagamento formaPagamento,
        @Positive(message = "Valor maior que zero") @NotNull(message = "Valor obrigatório")
        BigDecimal valor
) { }
