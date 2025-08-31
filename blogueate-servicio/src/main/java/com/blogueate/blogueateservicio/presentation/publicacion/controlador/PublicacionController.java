package com.blogueate.blogueateservicio.presentation.publicacion.controlador;

import com.blogueate.blogueateservicio.domain.publicacion.model.PublicacionModel;
import com.blogueate.blogueateservicio.domain.publicacion.service.PublicacionService;
import com.blogueate.blogueateservicio.infrastructure.configuration.seguridad.CustomUserDetails;
import com.blogueate.blogueateservicio.presentation.publicacion.dto.PublicacionListaDto;
import com.blogueate.blogueateservicio.presentation.publicacion.mapper.PublicacionListaDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PublicacionController {

    private final PublicacionService publicacionService;
    private final PublicacionListaDtoMapper mapper;

    @GetMapping
    public ResponseEntity<List<PublicacionListaDto>> listarPublicaciones() {
        List<PublicacionListaDto> publicaciones = publicacionService.listarPublicaciones()
                .stream()
                .map(mapper::toDto)
                .toList();
        return ResponseEntity.ok(publicaciones);
    }

    // 2️⃣ Listar las publicaciones propias del usuario autenticado
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mis-publicaciones")
    public ResponseEntity<List<PublicacionListaDto>> listarMisPublicaciones(Authentication authentication) {
        Long usuarioId = ((CustomUserDetails) authentication.getPrincipal()).getUsuario().getId(); // Asumiendo que tienes CustomUserDetails
        List<PublicacionListaDto> publicaciones = publicacionService.listarPublicacionPorUsuario(usuarioId)
                .stream()
                .map(mapper::toDto)
                .toList();

        return publicaciones.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(publicaciones);
    }

    // 🔹 Obtener publicación por ID
    @GetMapping("/{id}")
    public ResponseEntity<PublicacionModel> obtenerPublicacionPorId(@PathVariable Long id) {
        PublicacionModel publicacion = publicacionService.obtenerPublicacionPorId(id);
        return (publicacion == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(publicacion);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/mis-publicaciones/crear")
    public ResponseEntity<PublicacionListaDto> crearPublicacion(
            @RequestBody PublicacionModel publicacion) {

        // Llamamos directamente al service (él se encarga de usuario y categoría)
        PublicacionModel creada = publicacionService.crearPublicacion(publicacion);

        // Construir la URI del recurso recién creado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(mapper.toDto(creada));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/mis-publicaciones/{id}")
    public ResponseEntity<PublicacionModel> actualizarPublicacion(
            @PathVariable Long id,
            @RequestBody PublicacionModel publicacion) {
        PublicacionModel actualizado = publicacionService.actualizarPublicacion(id, publicacion);
        return ResponseEntity.ok(actualizado);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/mis-publicaciones/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long id) {
        publicacionService.eliminarPublicacion(id);
        return ResponseEntity.noContent().build();
    }
}