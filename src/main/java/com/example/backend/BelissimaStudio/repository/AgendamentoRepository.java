package com.example.backend.BelissimaStudio.repository;

import com.example.backend.BelissimaStudio.enums.StatusPagamento;
import com.example.backend.BelissimaStudio.model.Agendamento;
import com.example.backend.BelissimaStudio.enums.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    @Query("select coalesce(sum(a.valor), 0) from agendamento a where a.data = :data")
    BigDecimal somaProduzidoNoDia(LocalDate data);

    @Query("""
        select coalesce(sum(a.valor), 0) from agendamento a
        where a.data = :data and a.statusPagamento = :status
        """)
    BigDecimal somaPorStatusNoDia(LocalDate data, StatusPagamento status);

    @Query("""
        select a from agendamento a join fetch a.cliente
        where a.statusPagamento = com.example.backend.BelissimaStudio.enums.StatusPagamento.PENDENTE
          and a.data <= :ate
        order by a.data, a.horario
    """)
    List<Agendamento> findPendentes(LocalDate ate);

    @Query("""
        select a from agendamento a join fetch a.cliente
        where a.data = :data order by a.horario
    """)
    List<Agendamento> findMovimentacoesDoDia(LocalDate data);
}
