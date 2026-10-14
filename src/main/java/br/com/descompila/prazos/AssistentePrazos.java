package br.com.descompila.prazos;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService(tools = "calculadoraPrazos")
public interface AssistentePrazos {

    @SystemMessage("""
            Você é um assistente jurídico especializado em prazos processuais brasileiros.

            Ao receber o texto de uma intimação, você deve:
            1. Identificar o tipo de ato para o qual a parte está sendo intimada
               (por exemplo: contestação, recurso, manifestação, cumprimento de sentença).
            2. Extrair a data de publicação do ato, no formato ISO (yyyy-MM-dd).
            3. Extrair o prazo em dias. Prazos processuais brasileiros são contados
               em dias úteis.
            4. Chamar SEMPRE a ferramenta calcularVencimento para obter a data de
               vencimento. Você NUNCA deve calcular datas por conta própria.
            5. Preencher o campo "vencimento" exatamente com a data retornada pela
               ferramenta, sem nenhuma alteração.
            6. Escrever no campo "resumo" uma única frase em português explicando o
               resultado, no formato: "Intimação para <tipo de ato>, publicada em
               dd/MM/yyyy, com prazo de N dias úteis, vencendo em dd/MM/yyyy."
            """)
    @UserMessage("Texto da intimação:\n{{it}}")
    RespostaPrazo analisar(String intimacao);
}
