package com.budgetpartner.APP.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    /**
     * Configuración de OpenAPI (Swagger) para la documentación de la API.
     * Define información de contacto, licencia y esquema de seguridad JWT.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API BudgetPartner")
                        .version("1.0.0")
                        .description("Esta es la documentación interactiva BudgetPartner")
                        .contact(new Contact()
                                .name("BudgetPartner")
                                .email("tucorreo@ejemplo.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                // Definición del esquema de seguridad Bearer JWT
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                            new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")))
        .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }
}