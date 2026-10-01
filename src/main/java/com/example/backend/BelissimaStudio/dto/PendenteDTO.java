package com.example.backend.BelissimaStudio.dto;

import com.example.backend.BelissimaStudio.enums.Servico;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

public record PendenteDTO(
        Long agendamentoId,
        String cliente,
        LocalDate data,
        LocalTime horario,
        List<String> servicos,
        BigDecimal valor
) { }
