package com.blogueate.blogueateservicio.infrastructure.configuration.seguridad;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordTestConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void check() {
        String raw = "12345"; // contraseña en texto plano
        String hashed = "$2a$10$Dow1DWrVYzZ3x9BpjYUnhO4sSeZr97oiqMZzM9yGkn41SW/.slc5C"; // la de tu BD
        System.out.println("Matches? " + passwordEncoder.matches(raw, hashed));
    }

    @PostConstruct
    public void generate() {
        String raw = "12345";
        String hashed = passwordEncoder.encode(raw);
        System.out.println("Nuevo hash para '12345': " + hashed);
    }
}

