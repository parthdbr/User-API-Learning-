package com.swagger.swager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class swaggerConfig extends WebMvcConfigurationSupport {



        @Bean
        public Docket api() {


            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.swagger.swager"))
                    .paths(PathSelectors.any())
                    .build()
                    .apiInfo(apiInfo())
                    .securitySchemes(Collections.singletonList(apiKey()))
                    .securityContexts(securityContext());


            return docket;
        }


    private ApiKey apiKey() {
        return new ApiKey("apiKey", "Authorization", "header");
    }

    private List<SecurityContext> securityContext() {
        return List.of(SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build());
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("apiKey",
                authorizationScopes));
    }



    private ApiInfo apiInfo() {

        return new ApiInfo(
                "My REST API", //title
                "Some custom description of API.", //description
                "Version 1.0", //version
                "Terms of service", //terms of service URL
                new Contact("PD", "www.pd.com", "pd@company.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
