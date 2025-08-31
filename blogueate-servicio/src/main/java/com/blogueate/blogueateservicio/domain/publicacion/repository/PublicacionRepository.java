package com.blogueate.blogueateservicio.domain.publicacion.repository;

import com.blogueate.blogueateservicio.domain.publicacion.model.PublicacionModel;
import com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.entity.PublicacionEntity;

import java.util.List;
import java.util.Optional;

public interface PublicacionRepository {
    List<PublicacionModel> listar();
    List<PublicacionModel> listarPorUsuario(Long id);
    PublicacionModel obtenerPorId(Long id);
    PublicacionModel guardar(PublicacionModel publicacion);
    PublicacionModel actualizar(Long id, PublicacionModel publicacion);
    void eliminar(Long id);

    boolean existsByIdAndUsuarioId(Long publicacionId, Long usuarioId);
}