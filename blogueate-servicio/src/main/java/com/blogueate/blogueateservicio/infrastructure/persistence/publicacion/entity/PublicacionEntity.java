package com.blogueate.blogueateservicio.infrastructure.persistence.publicacion.entity;

import com.blogueate.blogueateservicio.infrastructure.configuration.AuditorAwareConfig;
import com.blogueate.blogueateservicio.infrastructure.persistence.categoria.entity.CategoriaEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.entity.UsuarioEntity;
import com.blogueate.blogueateservicio.infrastructure.persistence.shared.Auditoria;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "publicaciones") // nombre exacto de la tabla
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicacionEntity extends Auditoria<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name="resumen", nullable = false)
    private String resumen;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @CreationTimestamp
    @Column(name = "fecha_publicacion", updatable = false)
    private LocalDateTime fechaPublicacion;

    @Column(nullable = false)
    private Boolean activo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEntity categoria;
}
