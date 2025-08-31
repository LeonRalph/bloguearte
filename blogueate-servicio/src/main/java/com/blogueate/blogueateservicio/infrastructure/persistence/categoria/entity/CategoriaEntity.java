package com.blogueate.blogueateservicio.infrastructure.persistence.categoria.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias") // nombre exacto de la tabla
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}
