package com.example.backend.BelissimaStudio.dto;

import com.example.backend.BelissimaStudio.enums.FormaPagamento;
import java.math.BigDecimal;

public record FormaPagamentoTotalDTO(
        FormaPagamento forma,
        BigDecimal total,
        Long quantidade
) { }
