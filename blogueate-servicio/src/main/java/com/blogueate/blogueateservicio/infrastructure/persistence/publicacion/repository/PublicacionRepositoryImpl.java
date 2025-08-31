package com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.repository;

import com.blogueate.blogueateservicio.domain.publicacion.model.PublicacionModel;
import com.blogueate.blogueateservicio.domain.publicacion.repository.PublicacionRepository;
import com.blogueate.blogueateservicio.domain.seguridad.repository.UsuarioRepository;
import com.blogueate.blogueateservicio.infrastructure.persistence.categoria.jpa.CategoriaRepositoryJpa;
import com.blogueate.blogueateservicio.infrastructure.persistence.categoria.mapper.CategoriaMapper;
import com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.entity.PublicacionEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.jpa.PublicacionRepositoryJpa;
import com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.mapper.PublicacionMapper;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.jpa.UsuarioRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PublicacionRepositoryImpl implements PublicacionRepository {

    private final PublicacionRepositoryJpa publicacionRepositoryJpa;
    private final PublicacionMapper publicacionMapper;
    private final UsuarioRepositoryJpa usuarioRepositoryJpa;
    private final CategoriaRepositoryJpa categoriaRepositoryJpa;

    @Override
    public List<PublicacionModel> listar() {
        return publicacionRepositoryJpa.findAll().stream()
                .map(publicacionMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public PublicacionModel guardar(PublicacionModel publicacion) {
        // 1️⃣ Obtener referencias a usuario y categoría usando getReferenceById para no cargar lazy completo
        var usuarioEntity = usuarioRepositoryJpa.getReferenceById(publicacion.getUsuario().getId());
        var categoriaEntity = categoriaRepositoryJpa.getReferenceById(publicacion.getCategoria().getId());

        // Mapear model → entity
        PublicacionEntity entity = publicacionMapper.entityMap(publicacion);

        // Asignar relaciones
        entity.setUsuario(usuarioEntity);
        entity.setCategoria(categoriaEntity);

        // Guardar en JPA
        PublicacionEntity saved = publicacionRepositoryJpa.save(entity);

        // Mapear entity → model para devolver
        return publicacionMapper.map(saved);
    }

    @Override
    public PublicacionModel actualizar(Long id, PublicacionModel publicacion) {
        PublicacionEntity existente = publicacionRepositoryJpa.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        // Actualizar campos
        existente.setTitulo(publicacion.getTitulo());
        existente.setResumen(publicacion.getResumen());
        existente.setContenido(publicacion.getContenido());
        existente.setActivo(publicacion.getActivo() != null ? publicacion.getActivo() : true);

        if (publicacion.getCategoria() != null && publicacion.getCategoria().getId() != null) {
            var categoriaEntity = categoriaRepositoryJpa.getReferenceById(publicacion.getCategoria().getId());
            existente.setCategoria(categoriaEntity);
        }

        PublicacionEntity updated = publicacionRepositoryJpa.save(existente);
        return publicacionMapper.map(updated);
    }

    @Override
    public PublicacionModel obtenerPorId(Long id) {
        return publicacionRepositoryJpa.findById(id)
                .map(publicacionMapper::map)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada con id " + id));
    }

    @Override
    public void eliminar(Long id) {
        if (publicacionRepositoryJpa.existsById(id)) {
            publicacionRepositoryJpa.deleteById(id);
        } else {
            throw new RuntimeException("Publicación no encontrada");
        }
    }

    @Override
    public boolean existsByIdAndUsuarioId(Long publicacionId, Long usuarioId) {
        return publicacionRepositoryJpa.existsByIdAndUsuarioId(publicacionId, usuarioId);
    }

    @Override
    public List<PublicacionModel> listarPorUsuario(Long id) {
        return publicacionRepositoryJpa.findByUsuarioId(id).stream()
                .map(publicacionMapper::map)
                .collect(Collectors.toList());
    }
}
