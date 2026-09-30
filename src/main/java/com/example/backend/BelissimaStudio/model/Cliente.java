package com.example.backend.BelissimaStudio.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "clientes")
@Entity(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Agendamento> agendamentos = new ArrayList<>();
}
