package br.com.descompila.prazos;

import java.time.LocalDate;

public record RespostaPrazo(
        String tipoAto,
        LocalDate dataPublicacao,
        int diasUteis,
        LocalDate vencimento,
        String resumo
) {
}
