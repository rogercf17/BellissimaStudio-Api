package com.example.backend.BelissimaStudio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bellissima-studio")
public class HealthController {
    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
