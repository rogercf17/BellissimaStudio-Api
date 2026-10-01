package com.example.backend.BelissimaStudio.repository;

import com.example.backend.BelissimaStudio.dto.FaturamentoDiaDTO;
import com.example.backend.BelissimaStudio.dto.FormaPagamentoTotalDTO;
import com.example.backend.BelissimaStudio.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    @Query("select coalesce(sum(p.valor), 0) from pagamento p where p.agendamento.data = :data")
    BigDecimal somaRecebidaNoDia(LocalDate data);

    @Query("""
        select new com.example.backend.BelissimaStudio.dto.FormaPagamentoTotalDTO(
           p.formaPagamento, sum(p.valor), count(p))
        from pagamento p
        where p.agendamento.data = :data
        group by p.formaPagamento
    """)
    List<FormaPagamentoTotalDTO> totaisPorForma(LocalDate data);

    @Query("""
        select new com.example.backend.BelissimaStudio.dto.FaturamentoDiaDTO(
            a.data, sum(p.valor))
        from pagamento p join p.agendamento a
        where a.data between :inicio and :fim
        group by a.data
        order by a.data
    """)
    List<FaturamentoDiaDTO> faturamentoPorDia(LocalDate inicio, LocalDate fim);

    List<Pagamento> findByAgendamentoIdIn(Collection<Long> ids);
}
