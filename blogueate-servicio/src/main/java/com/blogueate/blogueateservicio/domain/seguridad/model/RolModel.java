package com.blogueate.blogueateservicio.domain.seguridad.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RolModel {
    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activo;
}
