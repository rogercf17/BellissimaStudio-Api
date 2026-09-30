package com.example.backend.BelissimaStudio.controller;

import com.example.backend.BelissimaStudio.dto.request.ClienteRequest;
import com.example.backend.BelissimaStudio.dto.response.ClienteResponse;
import com.example.backend.BelissimaStudio.service.ClienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bellissima-studio")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteResponse>> listAll() {
        return ResponseEntity.ok(service.listarClientes());
    }

    @PostMapping("/cliente")
    public ResponseEntity<ClienteResponse> create(@Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.cadastrarCliente(request));
    }

    @PutMapping("/cliente/atualizar/{id}")
    public ResponseEntity<ClienteResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(service.atualizarCliente(id, request));
    }

    public record StatusRequest(@NotNull Boolean ativo) {}
    @PatchMapping("/cliente/{id}/status")
    public ResponseEntity<Void> activeOrDesactive(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequest request
    ) {
        service.alterarStatus(id, request.ativo());
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/clientes/status/{isAtivo}")
    public ResponseEntity<List<ClienteResponse>> findByAtivo(@PathVariable Boolean isAtivo) {
        return ResponseEntity.ok(service.listarAtivos(isAtivo));
    }
}
