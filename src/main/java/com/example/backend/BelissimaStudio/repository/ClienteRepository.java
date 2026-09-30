package com.example.backend.BelissimaStudio.repository;

import com.example.backend.BelissimaStudio.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByAtivo(Boolean ativo);
}