package com.example.backend.BelissimaStudio.dto.response;

import com.example.backend.BelissimaStudio.model.Agendamento;
import com.example.backend.BelissimaStudio.enums.Servico;

import java.math.BigDecimal;
import java.util.List;

public record AgendamentoResponse(
        Long id,
        Long clienteId,
        String nomeCliente,
        String telefoneCliente,
        String data,
        String horario,
        List<String> servicos,
        BigDecimal valor
) {

    public AgendamentoResponse(Agendamento agendamento) {
        this(
                agendamento.getId(),
                agendamento.getCliente().getId(),
                agendamento.getCliente().getNome(),
                agendamento.getCliente().getTelefone(),
                agendamento.getData().toString(),
                agendamento.getHorario().toString(),
                agendamento.getServicos()
                        .stream()
                        .map(Servico::getNome)
                        .toList(),
                agendamento.getValor()
        );
    }
}