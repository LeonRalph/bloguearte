package com.blogueate.blogueateservicio.infrastructure.persistence.categoria.mapper;

import com.blogueate.blogueateservicio.domain.categoria.model.CategoriaModel;
import com.blogueate.blogueateservicio.infrastructure.persistence.categoria.entity.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    CategoriaModel map(CategoriaEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    CategoriaEntity entityMap(CategoriaModel model);
}