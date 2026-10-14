package br.com.descompila.prazos;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

@Component
public class CalculadoraPrazos {

    private final Set<LocalDate> feriados;

    public CalculadoraPrazos(PrazosProperties propriedades) {
        this.feriados = Set.copyOf(propriedades.feriados());
    }

    @Tool("""
            Calcula a data de vencimento de um prazo processual contado em dias úteis.
            A contagem começa no primeiro dia útil seguinte à data de publicação; o dia
            da publicação não conta. Sábados, domingos e feriados nacionais não são
            contados. O resultado é sempre um dia útil, no formato ISO (yyyy-MM-dd).
            """)
    public LocalDate calcularVencimento(
            @P("Data de publicação do ato, no formato ISO yyyy-MM-dd") LocalDate dataPublicacao,
            @P("Quantidade de dias úteis do prazo") int diasUteis) {
        if (diasUteis < 1) {
            throw new IllegalArgumentException("O prazo deve ser de pelo menos 1 dia útil.");
        }

        LocalDate data = dataPublicacao;
        int restantes = diasUteis;
        while (restantes > 0) {
            data = data.plusDays(1);
            if (diaUtil(data)) {
                restantes--;
            }
        }
        return data;
    }

    private boolean diaUtil(LocalDate data) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();
        return diaDaSemana != DayOfWeek.SATURDAY
                && diaDaSemana != DayOfWeek.SUNDAY
                && !feriados.contains(data);
    }
}
