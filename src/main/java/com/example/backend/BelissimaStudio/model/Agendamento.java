package com.example.backend.BelissimaStudio.model;

import com.example.backend.BelissimaStudio.enums.Servico;
import com.example.backend.BelissimaStudio.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Table(name = "agendamentos")
@Entity(name = "agendamento")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
public class Agendamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    private LocalDate data;

    private LocalTime horario;

    @ElementCollection(targetClass = Servico.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "agendamento_servicos", joinColumns = @JoinColumn(name = "agendamento_id"))
    @Enumerated(EnumType.STRING) @Column(name = "servico", nullable = false)
    private List<Servico> servicos = new ArrayList<>();

    @Column(name = "valor", nullable = false, precision = 10, scale = 2,
            columnDefinition = "numeric(10,2) default 0")
    private BigDecimal valor = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING) @Column(name = "pagamento", nullable = false)
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;
}
