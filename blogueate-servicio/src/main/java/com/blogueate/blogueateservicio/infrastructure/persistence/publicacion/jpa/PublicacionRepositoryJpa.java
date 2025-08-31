package com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.jpa;

import com.blogueate.blogueateservicio.domain.publicacion.model.PublicacionModel;
import com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.entity.PublicacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PublicacionRepositoryJpa extends JpaRepository<PublicacionEntity,Long> {
    List<PublicacionEntity> findByUsuarioId(Long id);

    boolean existsByIdAndUsuarioId(Long publicacionId, Long usuarioId);

}
