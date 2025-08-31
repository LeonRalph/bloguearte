package com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.repository;


import com.blogueate.blogueateservicio.domain.seguridad.model.RolModel;
import com.blogueate.blogueateservicio.domain.seguridad.model.UsuarioModel;
import com.blogueate.blogueateservicio.domain.seguridad.repository.UsuarioRepository;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.entity.RolEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.entity.UsuarioEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.entity.UsuarioRolEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.jpa.UsuarioRepositoryJpa;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.mapper.RolMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SeguridadRepositoryImpl implements UsuarioRepository {

    private final UsuarioRepositoryJpa usuarioRepositoryJpa;
    private final RolMapper rolMapper;

    @Override
    public Optional<UsuarioModel> usuarioPorUserName(String username) {
        return usuarioRepositoryJpa.usuarioPorUsername(username)
                .map(u -> {
                    log.info("UsuarioEntity -> {}", u);
                    u.getRoles().forEach(ur -> {
                        log.info("UsuarioRolEntity -> rol.id={}, rol.nombre={}",
                                ur.getRol().getId(), ur.getRol().getNombre());
                    });

                    return UsuarioModel.builder()
                            .id(u.getId())
                            .username(u.getUsername())
                            .email(u.getEmail())
                            .passwordHash(u.getPasswordHash()) // ⚠️ en Model puedes llamarlo password
                            .apellido(u.getApellido())
                            .nombre(u.getNombre())
                            .activo(u.getActivo())
                            .roles(
                                    u.getRoles()
                                            .stream()
                                            .map(ur -> RolModel.builder()
                                                    .id(ur.getRol().getId())
                                                    .nombre(ur.getRol().getNombre())
                                                    .build()
                                            )
                                            .collect(Collectors.toSet())
                            )
                            .build();
                });
    }

    @Override
    public void guardarToken(String token) {
        log.info(token);
    }

    @Override
    public String obtenerTokenCache(String username) {
        return "";
    }

    @Override
    public UsuarioModel obtenerPorId(Long usuarioId) {
        return null;
    }
}
