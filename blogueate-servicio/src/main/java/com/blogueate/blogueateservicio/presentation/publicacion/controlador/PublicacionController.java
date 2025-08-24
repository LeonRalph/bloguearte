package com.blogueate.blogueateservicio.presentation.publicacion.controlador;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publicaciones")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PublicacionController {
}
