package com.blogueate.blogueateservicio.presentation.publicacion.dto;

import com.blogueate.blogueateservicio.presentation.categoria.dto.CategoriaResponseDto;
import com.blogueate.blogueateservicio.presentation.seguridad.dto.UsuarioResponseDto;

import java.time.LocalDateTime;

public record PublicacionListaDto(
        Long id,
        String titulo,
        String resumen,
        String contenido,
        LocalDateTime fechaPublicacion,
        UsuarioResponseDto usuario,
        CategoriaResponseDto categoria
) {

}
