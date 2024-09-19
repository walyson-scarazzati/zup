package br.com.zup.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Pessoa API").version("v1").description("Projeto de cadastro de pessoas")
                        .contact(new Contact().name("Walyson Scarazzati da Silva")
                                .url("https://github.com/walyson-scarazzati/zup.git")
                                .email("walyson.scarazzati@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.com")))
                .tags(Arrays.asList(new Tag().name("Pessoas").description("Gerencia as pessoas")));
    }

}
