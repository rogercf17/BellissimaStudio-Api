package com.example.backend.BelissimaStudio.repository;

import com.example.backend.BelissimaStudio.model.Agendamento;
import com.example.backend.BelissimaStudio.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByDataAndHorario(LocalDate data, LocalTime horario);
    boolean existsByDataAndHorarioAndIdNot(LocalDate data, LocalTime horario, Long id);
    List<Agendamento> findByData(LocalDate data);
    List<Agendamento> findByServicosContaining(Servico servico);
    List<Agendamento> findByDataBetween(LocalDate inicio, LocalDate fim);
    List<Agendamento> findByClienteIdOrderByDataDescHorarioDesc(Long clienteId);
}
