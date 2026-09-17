package com.example.backend.BelissimaStudio.dto.request;

import java.time.*;
import java.util.List;
import jakarta.validation.constraints.*;

public record AgendamentoRequest(
        @NotBlank(message = "Nome do cliente é obrigatório")
        String nomeCliente,

        @NotNull(message = "Data do agendamento é obrigatória")
        @FutureOrPresent(message = "A data do agendamento deve ser no presente ou no futuro")
        LocalDate data,

        @NotNull(message = "Horário do agendamento é obrigatório")
        LocalTime horario,

        @NotEmpty(message = "Pelo menos um serviço deve ser selecionado")
        List<String> servicos
) { }
