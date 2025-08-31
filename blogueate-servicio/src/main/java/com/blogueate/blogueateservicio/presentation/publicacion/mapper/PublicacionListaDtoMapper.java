package com.blogueate.blogueateservicio.presentation.publicacion.mapper;

import com.blogueate.blogueateservicio.domain.categoria.model.CategoriaModel;
import com.blogueate.blogueateservicio.domain.publicacion.model.PublicacionModel;
import com.blogueate.blogueateservicio.domain.seguridad.model.RolModel;
import com.blogueate.blogueateservicio.domain.seguridad.model.UsuarioModel;
import com.blogueate.blogueateservicio.presentation.categoria.dto.CategoriaResponseDto;
import com.blogueate.blogueateservicio.presentation.publicacion.dto.PublicacionListaDto;
import com.blogueate.blogueateservicio.presentation.seguridad.dto.UsuarioResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PublicacionListaDtoMapper {

    // PublicacionModel -> PublicacionDto
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "mapUsuario")
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "mapCategoria")
    PublicacionListaDto toDto(PublicacionModel model);

    // ========== USUARIO ==========
    @Named("mapUsuario")
    default UsuarioResponseDto mapUsuario(UsuarioModel usuario) {
        if (usuario == null) return null;
        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getRoles()
                        .stream()
                        .map(RolModel::getNombre) // solo los nombres de roles
                        .toList()
        );
    }

    // ========== CATEGORIA ==========
    @Named("mapCategoria")
    default CategoriaResponseDto mapCategoria(CategoriaModel categoria) {
        if (categoria == null) return null;
        return new CategoriaResponseDto(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }
}