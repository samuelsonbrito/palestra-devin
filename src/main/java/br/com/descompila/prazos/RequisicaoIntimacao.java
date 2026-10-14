package br.com.descompila.prazos;

import jakarta.validation.constraints.NotBlank;

public record RequisicaoIntimacao(
        @NotBlank(message = "O texto da intimação não pode estar vazio.") String intimacao
) {
}
