package com.example.backend.BelissimaStudio.service;

import com.example.backend.BelissimaStudio.dto.request.AgendamentoRequest;
import com.example.backend.BelissimaStudio.dto.response.AgendamentoResponse;
import com.example.backend.BelissimaStudio.model.Agendamento;
import com.example.backend.BelissimaStudio.model.Cliente;
import com.example.backend.BelissimaStudio.model.Servico;
import com.example.backend.BelissimaStudio.repository.AgendamentoRepository;
import com.example.backend.BelissimaStudio.repository.ClienteRepository;
import com.example.backend.BelissimaStudio.uteis.ConverterServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static com.example.backend.BelissimaStudio.uteis.ConverterServico.converterServicos;


@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendamentoRepository repository;
    private final ClienteRepository clienteRepository;

    public List<AgendamentoResponse> listarAgendamentos() {
        return repository.findAll()
                .stream()
                .map(AgendamentoResponse::new)
                .toList();
    }

    public AgendamentoResponse criarAgendamento(AgendamentoRequest request) {
        boolean conflito = repository.existsByDataAndHorario(request.data(), request.horario());
        if (conflito) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Já existe um agendamento neste horário.");
        }

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrada com o ID: " + request.clienteId()
                ));

        Agendamento agendamento = new Agendamento();

        agendamento.setCliente(cliente);
        agendamento.setData(request.data());
        agendamento.setHorario(request.horario());
        agendamento.setServicos(converterServicos(request.servicos()));

        return new AgendamentoResponse(repository.save(agendamento));
    }

    public List<AgendamentoResponse> buscarAgendamentoPorData(LocalDate data) {
        List<Agendamento> agendamentosEncontrados = repository.findByData(data);

        if (agendamentosEncontrados.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nenhum agendamento encontrado para a data especificada.");
        }

        return agendamentosEncontrados.stream()
                .map(AgendamentoResponse::new)
                .toList();
    }

    public List<AgendamentoResponse> buscarAgendamentoPorMes(Integer mes, Integer ano) {
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

        List<Agendamento> agendamentosEncontrados = repository.findByDataBetween(inicio, fim);

        if (agendamentosEncontrados.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nenhum agendamento encontrado para o mês especificado.");
        }

        return agendamentosEncontrados.stream()
                .map(AgendamentoResponse::new)
                .toList();
    }

    public List<AgendamentoResponse> buscarAgendamentoPorServico(String servico) {
        Servico servicoEnum;

        try {
            servicoEnum = Servico.valueOf(servico.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Serviço inválido: " + servico);
        }

        List<Agendamento> agendamentosEncontrados = repository.findByServicosContaining(servicoEnum);

        if (agendamentosEncontrados.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Nenhum agendamento encontrado para o serviço especificado.");
        }

        return agendamentosEncontrados.stream()
                .map(AgendamentoResponse::new)
                .toList();
    }

    public void deletarAgendamento(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento não encontrado com o ID: " + id));

        repository.delete(agendamento);
    }

    public AgendamentoResponse atualizarAgendamento(Long id, AgendamentoRequest request) {
        Agendamento agendamentoExistente = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agendamento não encontrado com o ID: " + id));

        boolean dataOuHorarioAlterados = !agendamentoExistente.getData().equals(request.data()) ||
                !agendamentoExistente.getHorario().equals(request.horario());

        if (dataOuHorarioAlterados) {
            boolean conflito = repository.existsByDataAndHorarioAndIdNot(request.data(), request.horario(), id);
            if (conflito) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Já existe um agendamento neste horário.");
            }
        }

        Cliente cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrada com o ID: " + request.clienteId()
                ));

        agendamentoExistente.setCliente(cliente);
        agendamentoExistente.setData(request.data());
        agendamentoExistente.setHorario(request.horario());
        agendamentoExistente.setServicos(converterServicos(request.servicos()));

        return new AgendamentoResponse(repository.save(agendamentoExistente));
    }
}
