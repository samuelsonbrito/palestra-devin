package br.com.descompila.prazos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraPrazosTest {

    private CalculadoraPrazos calculadora;

    @BeforeEach
    void setUp() {
        List<LocalDate> feriados2026 = List.of(
                LocalDate.parse("2026-01-01"),
                LocalDate.parse("2026-02-16"),
                LocalDate.parse("2026-02-17"),
                LocalDate.parse("2026-04-03"),
                LocalDate.parse("2026-04-21"),
                LocalDate.parse("2026-05-01"),
                LocalDate.parse("2026-06-04"),
                LocalDate.parse("2026-09-07"),
                LocalDate.parse("2026-10-12"),
                LocalDate.parse("2026-11-02"),
                LocalDate.parse("2026-11-15"),
                LocalDate.parse("2026-11-20"),
                LocalDate.parse("2026-12-25")
        );
        calculadora = new CalculadoraPrazos(new PrazosProperties(feriados2026));
    }

    @Test
    void publicacaoNumaSextaFeiraComecaAContarNaSegunda() {
        // sexta-feira, 16/10/2026 — 1 dia útil vence na segunda, 19/10
        assertEquals(LocalDate.parse("2026-10-19"),
                calculadora.calcularVencimento(LocalDate.parse("2026-10-16"), 1));
    }

    @Test
    void prazoQueAtravessaFeriado() {
        // 15 dias úteis a partir de 16/10/2026 pulando o feriado de 02/11 (Finados)
        assertEquals(LocalDate.parse("2026-11-09"),
                calculadora.calcularVencimento(LocalDate.parse("2026-10-16"), 15));
    }

    @Test
    void prazoQueViraOMes() {
        // quinta-feira, 29/01/2026 — 3 dias úteis: 30/01, 02/02 e 03/02
        assertEquals(LocalDate.parse("2026-02-03"),
                calculadora.calcularVencimento(LocalDate.parse("2026-01-29"), 3));
    }

    @Test
    void publicacaoNaVesperaDeFeriado() {
        // segunda-feira, 20/04/2026 — véspera de Tiradentes (terça, 21/04)
        // 1 dia útil vence na quarta-feira, 22/04
        assertEquals(LocalDate.parse("2026-04-22"),
                calculadora.calcularVencimento(LocalDate.parse("2026-04-20"), 1));
    }

    @Test
    void prazoDeUmDiaUtil() {
        // quarta-feira, 14/10/2026 — vence na quinta, 15/10
        assertEquals(LocalDate.parse("2026-10-15"),
                calculadora.calcularVencimento(LocalDate.parse("2026-10-14"), 1));
    }

    @Test
    void publicacaoEmFeriadoComecaNoProximoDiaUtil() {
        // publicação no próprio feriado de Tiradentes — 1 dia útil vence em 22/04
        assertEquals(LocalDate.parse("2026-04-22"),
                calculadora.calcularVencimento(LocalDate.parse("2026-04-21"), 1));
    }

    @Test
    void rejeitaPrazoMenorQueUmDia() {
        assertThrows(IllegalArgumentException.class,
                () -> calculadora.calcularVencimento(LocalDate.parse("2026-10-16"), 0));
    }
}
