package br.com.zup.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableSwagger2
@Configuration
public class SwaggerConfig {
	

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .build();
    }

//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("br.com.zup.controller"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Pessoa API")
//                .description("Projeto de cadastro de pessoas")
//                .version("1.0.0")
//                .contact(contact())
//                .build();
//    }

//    private Contact contact() {
//        return new Contact("Walyson Scarazzati da Silva",
//                "https://github.com/walyson-scarazzati/zup.git",
//                "walsyon.scarazzati@gmail.com");
//    }
}
