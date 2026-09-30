package com.example.backend.BelissimaStudio.dto.response;

import com.example.backend.BelissimaStudio.model.Cliente;

import java.util.List;

public record ClienteResponse(
        Long id,
        String nome,
        String telefone,
        Boolean ativo,
        List<AgendamentoClienteResponse> agendamentos
) {

    public ClienteResponse(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getTelefone(),
                cliente.getAtivo(),
                cliente.getAgendamentos()
                        .stream()
                        .map(AgendamentoClienteResponse::new)
                        .toList()
        );
    }
}