package com.blogueate.blogueateservicio.presentation.seguridad.dto;

public record LoginResponseDto(
        String accessToken,
        String refreshToken,
        Long expiresIn
) {
}

