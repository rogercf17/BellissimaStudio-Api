package com.example.backend.BelissimaStudio.repository;

import com.example.backend.BelissimaStudio.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.*;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    boolean existsByDataAndHorario(LocalDate data, LocalTime horario);
    boolean existsByDataAndHorarioAndIdNot(LocalDate data, LocalTime horario, Long id);
    List<Agendamento> findByData(LocalDate data);
    List<Agendamento> findByServicosContaining(Servico servico);
}
