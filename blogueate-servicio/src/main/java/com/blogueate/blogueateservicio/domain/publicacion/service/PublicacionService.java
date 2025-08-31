package com.blogueate.blogueateservicio.domain.publicacion.service;

import com.blogueate.blogueateservicio.domain.publicacion.model.PublicacionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PublicacionService {
    List<PublicacionModel> listarPublicaciones();
    List<PublicacionModel> listarPublicacionPorUsuario(Long id);
    PublicacionModel obtenerPublicacionPorId(Long id);
    PublicacionModel crearPublicacion(PublicacionModel publicacion);
    PublicacionModel actualizarPublicacion(Long id, PublicacionModel publicacion);
    void eliminarPublicacion(Long id);


    boolean perteneceAlUsuario(Long id, Long usuarioId);
}
