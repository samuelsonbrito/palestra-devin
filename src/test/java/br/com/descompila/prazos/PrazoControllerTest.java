package br.com.descompila.prazos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PrazoController.class,
        properties = "langchain4j.open-ai.chat-model.api-key=chave-de-teste")
class PrazoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private AssistentePrazos assistente;

    @Test
    void retornaRespostaPrazo() throws Exception {
        RespostaPrazo resposta = new RespostaPrazo(
                "contestação",
                LocalDate.parse("2026-10-16"),
                15,
                LocalDate.parse("2026-11-09"),
                "Intimação para contestação, publicada em 16/10/2026, com prazo de 15 dias úteis, vencendo em 09/11/2026."
        );
        when(assistente.analisar(anyString())).thenReturn(resposta);

        mvc.perform(post("/api/prazos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"intimacao\": \"Fica a parte ré intimada para apresentar contestação...\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoAto").value("contestação"))
                .andExpect(jsonPath("$.dataPublicacao").value("2026-10-16"))
                .andExpect(jsonPath("$.diasUteis").value(15))
                .andExpect(jsonPath("$.vencimento").value("2026-11-09"))
                .andExpect(jsonPath("$.resumo").isString());
    }

    @Test
    void rejeitaIntimacaoVazia() throws Exception {
        mvc.perform(post("/api/prazos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"intimacao\": \"   \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("O texto da intimação não pode estar vazio."));
    }

    @Test
    void rejeitaRequisicaoSemCampoIntimacao() throws Exception {
        mvc.perform(post("/api/prazos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").isString());
    }
}
