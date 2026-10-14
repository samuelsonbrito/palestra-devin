package br.com.descompila.prazos;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prazos")
public class PrazoController {

    private final AssistentePrazos assistente;

    public PrazoController(AssistentePrazos assistente) {
        this.assistente = assistente;
    }

    @PostMapping
    public RespostaPrazo calcular(@Valid @RequestBody RequisicaoIntimacao requisicao) {
        return assistente.analisar(requisicao.intimacao());
    }
}
