package com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.mapper;

import com.blogueate.blogueateservicio.domain.publicacion.model.PublicacionModel;
import com.blogueate.blogueateservicio.infrastructure.persistence.categoria.mapper.CategoriaMapper;
import com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.entity.PublicacionEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.mapper.UsuarioMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, CategoriaMapper.class})
public interface PublicacionMapper {

    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "categoria", source = "categoria")
    PublicacionModel map(PublicacionEntity entity);

    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "categoria", source = "categoria")
    PublicacionEntity entityMap(PublicacionModel model);
}