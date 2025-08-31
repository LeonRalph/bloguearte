package com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.mapper;

import com.blogueate.blogueateservicio.domain.seguridad.model.RolModel;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.entity.RolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper {
    RolModel toModel(RolEntity entity);
    RolEntity toEntity(RolModel model);
}