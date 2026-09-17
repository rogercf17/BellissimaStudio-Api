package com.example.backend.BelissimaStudio.controller;

import com.example.backend.BelissimaStudio.dto.request.AgendamentoRequest;
import com.example.backend.BelissimaStudio.dto.response.AgendamentoResponse;
import com.example.backend.BelissimaStudio.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/bellissima-studio")
public class AgendamentoController {
    @Autowired
    private AgendamentoService service;

    @GetMapping("/agendamentos")
    public ResponseEntity<List<AgendamentoResponse>> listAll() {
        List<AgendamentoResponse> agendamentos = service.listarAgendamentos();
        return ResponseEntity.ok(agendamentos);
    }

    @PostMapping("/agendamento")
    public ResponseEntity<AgendamentoResponse> create(@Valid @RequestBody AgendamentoRequest request) {
        AgendamentoResponse agendamento = service.criarAgendamento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamento);
    }

    @GetMapping("/agendamento/data/{data}")
    public ResponseEntity<List<AgendamentoResponse>> findByDate(@PathVariable LocalDate data) {
        List<AgendamentoResponse> agendamentos = service.buscarAgendamentoPorData(data);
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/agendamento/servico/{servico}")
    public ResponseEntity<List<AgendamentoResponse>> findByServico(@PathVariable String servico) {
        List<AgendamentoResponse> agendamentos = service.buscarAgendamentoPorServico(servico);
        return ResponseEntity.ok(agendamentos);
    }

    @DeleteMapping("/agendamento/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deletarAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/agendamento/atualizar/{id}")
    public ResponseEntity<AgendamentoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AgendamentoRequest request) {
        AgendamentoResponse agendamentoAtualizado = service.atualizarAgendamento(id, request);
        return ResponseEntity.ok(agendamentoAtualizado);
    }
}