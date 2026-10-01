package com.example.backend.BelissimaStudio.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AgendamentoRequest(
        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "Data do agendamento é obrigatória")
        @FutureOrPresent(message = "A data do agendamento deve ser no presente ou no futuro")
        LocalDate data,

        @NotNull(message = "Horário do agendamento é obrigatório")
        LocalTime horario,

        @NotEmpty(message = "Pelo menos um serviço deve ser selecionado")
        List<String> servicos,

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser maior que zero")
        BigDecimal valor
) {
}