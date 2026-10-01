package com.example.backend.BelissimaStudio.dto.response;

import com.example.backend.BelissimaStudio.model.Agendamento;
import com.example.backend.BelissimaStudio.enums.Servico;

import java.util.List;

public record AgendamentoClienteResponse(
        Long id,
        String data,
        String horario,
        List<String> servicos
) {

    public AgendamentoClienteResponse(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getData().toString(),
                agendamento.getHorario().toString(),
                agendamento.getServicos()
                        .stream()
                        .map(Servico::getNome)
                        .toList()
        );
    }
}