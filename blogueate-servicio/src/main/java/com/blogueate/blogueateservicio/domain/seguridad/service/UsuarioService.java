package com.blogueate.blogueateservicio.domain.seguridad.service;

import com.blogueate.blogueateservicio.domain.seguridad.model.UsuarioModel;

import java.util.Optional;

public interface UsuarioService {
    Optional<UsuarioModel> usuarioPorUserName(String username);

    void guardarToken(String token);

    String obtenerTokenCache(String username);
}
