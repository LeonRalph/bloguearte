package com.blogueate.blogueateservicio.domain.publicacion.model;

import com.blogueate.blogueateservicio.domain.categoria.model.CategoriaModel;
import com.blogueate.blogueateservicio.domain.seguridad.model.UsuarioModel;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublicacionModel {

    private Long id;
    private String titulo;
    private String resumen;
    private String contenido;
    private LocalDateTime fechaPublicacion;
    private Boolean activo;
    private UsuarioModel usuario;
    private CategoriaModel categoria;
}
