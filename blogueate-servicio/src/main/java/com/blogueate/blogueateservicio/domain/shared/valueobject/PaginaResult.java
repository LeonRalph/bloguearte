package com.blogueate.blogueateservicio.domain.shared.valueobject;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PaginaResult<T> {
    List<T> contenido;
    int paginaActual;
    int tamanio;
    long totalElementos;
    int totalPaginas;
    boolean primera;
    boolean ultima;
    boolean vacia;

    public static <T> PaginaResult<T> of(List<T> contenido, int pagina, int tamanio, long total) {
        int totalPaginas = (int) Math.ceil((double) total / tamanio);
        return PaginaResult.<T>builder()
                .contenido(contenido)
                .paginaActual(pagina)
                .tamanio(tamanio)
                .totalElementos(total)
                .totalPaginas(totalPaginas)
                .primera(pagina == 0)
                .ultima(pagina >= totalPaginas - 1)
                .vacia(contenido.isEmpty())
                .build();
    }
}
