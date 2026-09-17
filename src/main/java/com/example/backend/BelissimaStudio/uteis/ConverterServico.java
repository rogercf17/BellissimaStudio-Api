package com.example.backend.BelissimaStudio.uteis;

import com.example.backend.BelissimaStudio.model.Servico;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.stream.Collectors;

public class ConverterServico {
    public static Servico converterServico(String valor) {
        try {
            return Servico.valueOf(valor.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Serviço inválido: " + valor);
        }
    }

    public static List<Servico> converterServicos(List<String> valores) {
        return valores.stream()
                .map(ConverterServico::converterServico)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
