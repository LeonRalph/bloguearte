package com.blogueate.blogueateservicio.application.seguridad.usecase;

import com.blogueate.blogueateservicio.domain.seguridad.exception.CredencialesInvalidasException;
import com.blogueate.blogueateservicio.domain.seguridad.model.SeguridadModel;
import com.blogueate.blogueateservicio.domain.seguridad.model.UsuarioModel;
import com.blogueate.blogueateservicio.domain.seguridad.service.TokenService;
import com.blogueate.blogueateservicio.infrastructure.configuration.seguridad.CustomUserDetails;
import com.blogueate.blogueateservicio.infrastructure.persistence.seguridad.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefrescarTokenUseCase {

    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;

    public SeguridadModel ejecutar(String refreshToken) {
        if (!tokenService.esTokenValido(refreshToken)) {
            log.warn("Intento de uso de refresh token inválido: {}", refreshToken);
            throw new CredencialesInvalidasException("Refresh token inválido o expirado");
        }
        String username = tokenService.extraerUsuario(refreshToken);
        return generarTokensDesdeUsername(username);
    }

    private SeguridadModel generarTokensDesdeUsername(String username) {
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        UsuarioModel usuario = userDetails.getUsuario();

        String accessToken = tokenService.generarTokenAcceso(usuario);
        String nuevoRefreshToken = tokenService.generarTokenRefresco(usuario);

        return SeguridadModel.builder()
                .token(accessToken)
                .refresh(nuevoRefreshToken)
                .build();
    }
}
