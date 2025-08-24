package com.blogueate.blogueateservicio.domain.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Estado {
    ACTIVO(true, "Registro activo"),
    INACTIVO(false, "Registro inactivo");

    private boolean valor;
    private String descripcion;

    public static Estado buscar(boolean valor) {
        return Arrays.stream(Estado.values())
                .filter(e -> e.valor == valor)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", valor)));
    }

    public static boolean obtenerValor(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        return estado.isValor();
    }
}