package com.example.backend.BelissimaStudio.dto.response;

import com.example.backend.BelissimaStudio.model.Agendamento;
import com.example.backend.BelissimaStudio.model.Servico;
import java.util.List;

public record AgendamentoResponse(
        Long id,
        String nomeCliente,
        String data,
        String horario,
        List<String> servicos
) {
    public AgendamentoResponse(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getNomeCliente(),
                agendamento.getData().toString(),
                agendamento.getHorario().toString(),
                agendamento.getServicos().stream()
                        .map(Servico::getNome)
                        .toList()
        );
    }
}
