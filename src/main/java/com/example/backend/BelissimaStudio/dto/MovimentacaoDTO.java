package com.example.backend.BelissimaStudio.dto;

import com.example.backend.BelissimaStudio.enums.FormaPagamento;
import com.example.backend.BelissimaStudio.enums.StatusPagamento;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

public record MovimentacaoDTO(
        Long agendamentoId,
        String cliente,
        LocalDate data,
        LocalTime horario,
        List<String> servicos,
        BigDecimal valor,
        StatusPagamento status,
        FormaPagamento formaPagamento   // null enquanto estiver pendente
) { }
