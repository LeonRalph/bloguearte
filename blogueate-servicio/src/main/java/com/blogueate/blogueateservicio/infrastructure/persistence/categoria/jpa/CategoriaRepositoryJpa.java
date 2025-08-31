package com.blogueate.blogueateservicio.infrastructure.persistence.categoria.jpa;

import com.blogueate.blogueateservicio.infrastructure.persistence.categoria.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositoryJpa extends JpaRepository<CategoriaEntity,Long> {
}
