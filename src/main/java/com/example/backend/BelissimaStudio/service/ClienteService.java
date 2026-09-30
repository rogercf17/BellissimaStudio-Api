package com.example.backend.BelissimaStudio.service;

import com.example.backend.BelissimaStudio.dto.request.ClienteRequest;
import com.example.backend.BelissimaStudio.dto.response.ClienteResponse;
import com.example.backend.BelissimaStudio.model.Cliente;
import com.example.backend.BelissimaStudio.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    public List<ClienteResponse> listarClientes() {
        return  repository.findAll().stream()
                .map(ClienteResponse::new)
                .toList();
    }

    public ClienteResponse cadastrarCliente(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setTelefone(request.telefone());

        return new ClienteResponse(repository.save(cliente));
    }

    public ClienteResponse atualizarCliente(Long id, ClienteRequest requestAtualizado) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrada com o ID: " + id));

        clienteExistente.setNome(requestAtualizado.nome());
        clienteExistente.setTelefone(requestAtualizado.telefone());

        return new ClienteResponse(repository.save(clienteExistente));
    }

    @Transactional
    public void alterarStatus(Long id, boolean ativo) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente não encontrado com o ID: " + id));
        cliente.setAtivo(ativo);
        repository.save(cliente);
    }

    public List<ClienteResponse> listarAtivos(Boolean isAtivo) {
        return repository.findByAtivo(isAtivo)
                .stream()
                .map(ClienteResponse::new)
                .toList();
    }
}
