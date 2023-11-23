package com.ggs.rabbitmq.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;

/**
 * @Author starbug
 * @Description
 * @Datetime 2023/11/23 11:04
 */
@Configuration
public class Swagger3Config {

    //配置一个test-public组
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("test-public")
                .packagesToScan("com.ggs.rabbitmq.demo")
//                                .pathsToMatch("/material/**")
                .addOperationCustomizer(operationCustomizer())
                .build();
    }

    //配置其他组
    @Bean
    public GroupedOpenApi otherApi() {
        return GroupedOpenApi.builder()
                .group("test-other")
                .packagesToScan("com.ggs.rabbitmq.demo")
//                                .pathsToMatch("/other/**")
                .build();
    }

    /**
     * 给所有@Operation注释的接口添加一个tellerno请求头参数
     */
    @Bean
    public OperationCustomizer operationCustomizer() {
        return new OperationCustomizer() {

            @Override
            public Operation customize(Operation operation, HandlerMethod handlerMethod) {
                operation.addParametersItem(
                        new Parameter()
                                .in(ParameterIn.HEADER.toString())
                                .name("tellerno")
                                .description("登录用户账号")
                                .schema(new StringSchema())
                                .required(false)
                );
                return operation;
            }
        };
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Swagger3 test API")
                        .description("Swagger3 test sample application")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

}
