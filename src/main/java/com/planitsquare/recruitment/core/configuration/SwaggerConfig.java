package com.planitsquare.recruitment.core.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("Holiday Keeper API")
                .version(springdocVersion)
                .description("Holiday Keeper API 문서입니다.");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
