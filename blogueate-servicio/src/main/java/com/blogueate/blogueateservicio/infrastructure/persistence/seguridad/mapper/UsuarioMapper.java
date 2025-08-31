package com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.mapper;

import com.blogueate.blogueateservicio.domain.seguridad.model.RolModel;
import com.blogueate.blogueateservicio.domain.seguridad.model.UsuarioModel;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.entity.UsuarioEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.entity.UsuarioRolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface UsuarioMapper {

    @Mapping(target = "roles", expression = "java(mapRoles(entity))")
    UsuarioModel map(UsuarioEntity entity);

    UsuarioEntity entityMap(UsuarioModel model);

    // 👇 MapStruct generará la llamada
    default Set<RolModel> mapRoles(UsuarioEntity entity) {
        if (entity == null || entity.getRoles() == null) return null;
        return entity.getRoles().stream()
                .map(usuarioRol -> toRolModel(usuarioRol))
                .collect(java.util.stream.Collectors.toSet());
    }

    // Se apoya en el RolMapper inyectado
    default RolModel toRolModel(UsuarioRolEntity usuarioRolEntity) {
        return usuarioRolEntity != null ?
                RolModel.builder()
                        .id(usuarioRolEntity.getRol().getId())
                        .nombre(usuarioRolEntity.getRol().getNombre())
                        .descripcion(usuarioRolEntity.getRol().getDescripcion())
                        .activo(usuarioRolEntity.getRol().getActivo())
                        .build()
                : null;
    }
}