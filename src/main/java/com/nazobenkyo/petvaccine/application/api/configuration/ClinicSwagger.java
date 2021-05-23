package com.nazobenkyo.petvaccine.application.api.configuration;

import com.nazobenkyo.petvaccine.application.api.security.jwt.JWTConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class ClinicSwagger {
    @Bean
    public Docket apiSwagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiV1Info())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nazobenkyo.petvaccine"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiV1Info() {
        return new ApiInfoBuilder()
                .title("Clinic API V1")
                .description("Clinic API swagger documentation")
                .version("v1")
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", JWTConstants.HEADER_AUTHENTICATION_KEY, "header");
    }
}
