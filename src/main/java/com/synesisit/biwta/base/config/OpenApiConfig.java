package com.synesisit.biwta.base.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {



    @Bean
    public OpenAPI openAPIConfiguration(){
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI().components(new Components())
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .info(new Info().title("On Board Survey").description("On Board Survey API")
                .termsOfService("http://synensisit.com.bd")
                .license(new License().name("Synesis IT Limited").url("http://synensisit.com.bd")));
    }
}
