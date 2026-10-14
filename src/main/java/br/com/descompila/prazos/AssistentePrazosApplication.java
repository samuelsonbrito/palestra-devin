package br.com.descompila.prazos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AssistentePrazosApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssistentePrazosApplication.class, args);
    }
}
