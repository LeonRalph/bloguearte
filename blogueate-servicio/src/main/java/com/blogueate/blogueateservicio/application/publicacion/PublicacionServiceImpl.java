package com.blogueate.blogueateservicio.application.publicacion;

import com.blogueate.blogueateservicio.domain.publicacion.model.PublicacionModel;
import com.blogueate.blogueateservicio.domain.publicacion.repository.PublicacionRepository;
import com.blogueate.blogueateservicio.domain.publicacion.service.PublicacionService;
import com.blogueate.blogueateservicio.infrastructure.configuration.AuditorAwareConfig;
import com.blogueate.blogueateservicio.infrastructure.persistence.categoria.entity.CategoriaEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.categoria.jpa.CategoriaRepositoryJpa;
import com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.entity.PublicacionEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.jpa.PublicacionRepositoryJpa;
import com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.mapper.PublicacionMapper;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.entity.UsuarioEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.jpa.UsuarioRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicacionServiceImpl implements PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepositoryJpa usuarioRepositoryJpa;
    private final CategoriaRepositoryJpa categoriaRepositoryJpa;
    private  final PublicacionRepositoryJpa publicacionRepositoryJpa;
    private final PublicacionMapper publicacionMapper;
    private final AuditorAwareConfig auditorAwareConfig;

    @Override
    public PublicacionModel crearPublicacion(PublicacionModel publicacion) {
        // Obtener username del usuario autenticado
        String username = auditorAwareConfig.getCurrentAuditor()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Buscar usuario autenticado en DB
        UsuarioEntity usuarioEntity = usuarioRepositoryJpa.usuarioPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Mapear modelo a entidad
        PublicacionEntity publicacionEntity = publicacionMapper.entityMap(publicacion);

        publicacionEntity.setActivo(true);

        // Asignar usuario autenticado
        publicacionEntity.setUsuario(usuarioEntity);

        // Traer la categoría completa de la DB
        CategoriaEntity categoriaEntity = categoriaRepositoryJpa.findById(publicacion.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        publicacionEntity.setCategoria(categoriaEntity);

        // Guardar en DB
        PublicacionEntity creadaEntity = publicacionRepositoryJpa.save(publicacionEntity);

        // Convertir a modelo y devolver
        return publicacionMapper.map(creadaEntity);
    }

    @Override
    public PublicacionModel actualizarPublicacion(Long id, PublicacionModel publicacion) {
        String username;
        username = auditorAwareConfig.getCurrentAuditor()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioEntity usuarioEntity = usuarioRepositoryJpa.usuarioPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PublicacionEntity existente = publicacionRepositoryJpa.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        boolean esPropietario = existente.getUsuario().getId().equals(usuarioEntity.getId());
        boolean esAdmin = usuarioEntity.getRoles().stream()
                .anyMatch(r -> r.getRol().getNombre().equalsIgnoreCase("ADMIN"));

        if (!esPropietario && !esAdmin) {
            throw new RuntimeException("No tienes permiso para actualizar esta publicación");
        }

        existente.setTitulo(publicacion.getTitulo());
        existente.setResumen(publicacion.getResumen());
        existente.setContenido(publicacion.getContenido());

        if (publicacion.getCategoria() != null && publicacion.getCategoria().getId() != null) {
            CategoriaEntity categoriaEntity = categoriaRepositoryJpa.findById(publicacion.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            existente.setCategoria(categoriaEntity);
        }

        if (publicacion.getActivo() != null) {
            existente.setActivo(publicacion.getActivo());
        } else if (existente.getActivo() == null) {
            existente.setActivo(true);
        }

        PublicacionEntity actualizada = publicacionRepositoryJpa.save(existente);
        return publicacionMapper.map(actualizada);
    }

    @Override
    public PublicacionModel obtenerPublicacionPorId(Long id) {
        return publicacionRepository.obtenerPorId(id);
    }

    @Override
    public List<PublicacionModel> listarPublicaciones() {
        return publicacionRepository.listar();
    }

    @Override
    public List<PublicacionModel> listarPublicacionPorUsuario(Long usuarioId) {
        return publicacionRepository.listarPorUsuario(usuarioId);
    }

    @Override
    public void eliminarPublicacion(Long id) {
        String username = auditorAwareConfig.getCurrentAuditor()
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioEntity usuarioEntity = usuarioRepositoryJpa.usuarioPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PublicacionEntity publicacionEntity = publicacionRepositoryJpa.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        boolean esPropietario = publicacionEntity.getUsuario().getId().equals(usuarioEntity.getId());
        boolean esAdmin = usuarioEntity.getRoles().stream()
                .anyMatch(rol -> "ADMIN".equalsIgnoreCase(rol.getRol().getNombre()));

        if (!esPropietario && !esAdmin) {
            throw new RuntimeException("No tienes permiso para eliminar esta publicación");
        }

        publicacionRepositoryJpa.deleteById(id);
    }



    @Override
    public boolean perteneceAlUsuario(Long id, Long usuarioId) {
        return publicacionRepository.existsByIdAndUsuarioId(id, usuarioId);
    }
}