package com.blogueate.blogueateservicio.domain.seguridad.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UsuarioModel {
    private Long id;
    private String username;
    private String email;
    private String passwordHash;
    private String nombre;
    private String apellido;
    private boolean activo;
    private Set<RolModel> roles;
}

