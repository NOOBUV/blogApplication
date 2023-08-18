package com.app.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =@Info(
                title = API.title,
                version = API.version,
                contact = @Contact(
                        name = "Utkarsh Vijay", email = "vijayutkarsh99@gmail.com", url = "https://www.linkedin.com/in/utkarshvijay9"
                ),
                description = API.description
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
//https://www.baeldung.com/openapi-jwt-authentication
public class SwaggerConfig {
}