package com.blogueate.blogueateservicio.presentation.seguridad.dto;

import java.util.List;

public record UsuarioResponseDto(
        Long id,
        String username,
        String email,
        String nombre,
        String apellido,
        List<String> roles
) {
}
