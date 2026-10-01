package com.example.backend.BelissimaStudio.controller;

import com.example.backend.BelissimaStudio.dto.*;
import com.example.backend.BelissimaStudio.service.PagamentoService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bellissima-studio/financeiro")
@RequiredArgsConstructor
public class PagamentoController {
    private final PagamentoService service;

    @GetMapping("/resumo")
    public ResponseEntity<ResumoDoDia> resumo(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
       return ResponseEntity.ok(service.resumoDoDia(data));
    }

    @GetMapping("/movimentacoes")
    public ResponseEntity<List<MovimentacaoDTO>> movimentacoes(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        return ResponseEntity.ok(service.movimentacoes(data));
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<PendenteDTO>> pendente() {
        return ResponseEntity.ok(service.pendentes());
    }

    @GetMapping("/faturamento")
    public ResponseEntity<List<FaturamentoDiaDTO>> faturamento(
            @RequestParam(defaultValue = "7") int dias
    ) {
        return ResponseEntity.ok(service.faturamento(dias));
    }

    @PostMapping("/agendamentos/{id}/pagamento")
    public ResponseEntity<Void> pagamento(
            @PathVariable("id") Long agendamentoId,
            @Valid @RequestBody RegistrarPagamentoDTO pagamentoDTO
    ) {
        service.registraPagamento(agendamentoId, pagamentoDTO);
        return ResponseEntity.noContent().build();
    }
}
