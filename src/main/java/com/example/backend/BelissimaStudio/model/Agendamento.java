package com.example.backend.BelissimaStudio.model;

import jakarta.persistence.*;
import lombok.*;
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

    private String nomeCliente;

    private LocalDate data;

    private LocalTime horario;

    @ElementCollection(targetClass = Servico.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "agendamento_servicos", joinColumns = @JoinColumn(name = "agendamento_id"))
    @Enumerated(EnumType.STRING) @Column(name = "servico", nullable = false)
    private List<Servico> servicos = new ArrayList<>();
}
