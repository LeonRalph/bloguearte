package com.blogueate.blogueateservicio.domain.seguridad.service;

import com.blogueate.blogueateservicio.domain.seguridad.model.SeguridadModel;

public interface SeguridadService {
    SeguridadModel autenticacion(String username, String password);

    SeguridadModel refrescar(String token);
}
