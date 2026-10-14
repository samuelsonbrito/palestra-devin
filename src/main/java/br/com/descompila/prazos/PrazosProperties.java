package br.com.descompila.prazos;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;
import java.util.List;

@ConfigurationProperties(prefix = "prazos")
public record PrazosProperties(List<LocalDate> feriados) {
}
