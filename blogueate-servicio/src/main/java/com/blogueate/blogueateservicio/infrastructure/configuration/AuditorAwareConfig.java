package com.blogueate.blogueateservicio.infrastructure.configuration;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration              // 🔹 Le dice a Spring que registre esta clase
public class AuditorAwareConfig implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return obtenerUsuarioActual();
    }

    private static Optional<String> obtenerUsuarioActual() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Optional.empty();
        }
        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String principal) {
            username = principal;
        }
        return Optional.ofNullable(username);
    }
}
