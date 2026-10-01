package com.example.backend.BelissimaStudio.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturamentoDiaDTO(
        LocalDate data,
        BigDecimal total
) { }
