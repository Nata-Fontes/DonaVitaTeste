package com.donavita.avaliacoes_api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "API de Avaliações - DonaVita",
        version = "1.0",
        description = "API RESTful CRUD para gerenciamento de avaliações e feedbacks da plataforma DonaVita",
        contact = @Contact(name = "Equipe DonaVita - Engenharia de Software")
    )
)
@SpringBootApplication
public class AvaliacoesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvaliacoesApiApplication.class, args);
    }
}
