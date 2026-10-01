package com.example.backend.BelissimaStudio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ResumoDoDia(
        LocalDate data,
        BigDecimal produzido,
        BigDecimal recebido,
        BigDecimal pendente,
        long totalServicos,
        long totalAtendimentos,
        List<FormaPagamentoTotalDTO> porForma
) { }
