package com.example.backend.BelissimaStudio.service;

import com.example.backend.BelissimaStudio.dto.*;
import com.example.backend.BelissimaStudio.enums.FormaPagamento;
import com.example.backend.BelissimaStudio.enums.Servico;
import com.example.backend.BelissimaStudio.enums.StatusPagamento;
import com.example.backend.BelissimaStudio.model.Agendamento;
import com.example.backend.BelissimaStudio.model.Pagamento;
import com.example.backend.BelissimaStudio.repository.AgendamentoRepository;
import com.example.backend.BelissimaStudio.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagamentoService {
    private static final ZoneId ZONA = ZoneId.of("America/Sao_Paulo");
    private final PagamentoRepository repository;
    private final AgendamentoRepository agendamentoRepository;

    private LocalDate hoje() {
        return LocalDate.now(ZONA);
    }

    @Transactional(readOnly = true)
    public ResumoDoDia resumoDoDia(LocalDate data) {
        LocalDate dia = data != null ? data : hoje();
        List<Agendamento> agendamentosDoDia = agendamentoRepository.findByData(dia);
        long totalServicos = agendamentosDoDia
                .stream()
                .mapToLong(a -> a.getServicos().size())
                .sum();

        return new ResumoDoDia(
                data,
                agendamentoRepository.somaProduzidoNoDia(data),
                repository.somaRecebidaNoDia(data),
                agendamentoRepository.somaPorStatusNoDia(data, StatusPagamento.PENDENTE),
                totalServicos,
                agendamentosDoDia.size(),
                repository.totaisPorForma(data)
        );
    }

    @Transactional(readOnly = true)
    public List<MovimentacaoDTO> movimentacoes(LocalDate data) {
        LocalDate dia = data != null ? data : hoje();
        List<Agendamento> agendamentos = agendamentoRepository.findMovimentacoesDoDia(dia);
        if (agendamentos.isEmpty()) return List.of();

        Map<Long, FormaPagamento> formas = repository
                .findByAgendamentoIdIn(agendamentos
                        .stream()
                        .map(Agendamento::getId).toList())
                .stream()
                .collect(Collectors.toMap(
                        p -> p.getAgendamento().getId(),
                        Pagamento::getFormaPagamento));

        return agendamentos
                .stream()
                .map(a -> new MovimentacaoDTO(
                        a.getId(), a.getCliente().getNome(), a.getData(),
                        a.getHorario(),
                        a.getServicos().stream().map(Servico::getNome).toList(),
                        a.getValor(), a.getStatusPagamento(), formas.get(a.getId())
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PendenteDTO> pendentes() {
        return agendamentoRepository.findPendentes(hoje())
                .stream()
                .map(a -> new PendenteDTO(
                        a.getId(), a.getCliente().getNome(),
                        a.getData(), a.getHorario(),
                        a.getServicos().stream().map(Servico::getNome).toList(),
                        a.getValor()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FaturamentoDiaDTO> faturamento(int dias) {
        if (dias != 7 && dias != 30 && dias != 90)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "dias deve ser 7, 30 ou 90"
            );

        LocalDate fim = LocalDate.now();
        LocalDate inicio = fim.minusDays(dias - 1L);

        Map<LocalDate, BigDecimal> porDia = repository.faturamentoPorDia(inicio, fim)
                .stream()
                .collect(Collectors.toMap(FaturamentoDiaDTO::data, FaturamentoDiaDTO::total));

        return inicio.datesUntil(fim.plusDays(1))
                .map(d -> new FaturamentoDiaDTO(d, porDia.getOrDefault(d, BigDecimal.ZERO)))
                .toList();
    }

    @Transactional
    public void registraPagamento(Long agendamentoId, RegistrarPagamentoDTO pagamentoDTO) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento não encontrado"));

        if (agendamento.getStatusPagamento() == StatusPagamento.PAGO)
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Agendamento já está pago");

        if (agendamento.getData().isAfter(hoje()))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Não é possível registrar pagamento de atendimento futuro");

        Pagamento pagamento = new Pagamento();
        pagamento.setAgendamento(agendamento);
        pagamento.setFormaPagamento(pagamentoDTO.formaPagamento());
        pagamento.setValor(pagamentoDTO.valor());
        pagamento.setDataPagamento(LocalDate.now());

        repository.save(pagamento);

        agendamento.setStatusPagamento(StatusPagamento.PAGO);
    }
}
