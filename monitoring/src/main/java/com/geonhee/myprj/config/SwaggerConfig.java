package com.geonhee.myprj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.geonhee.myprj.web"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false); 
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Monitoring API Documentation")
                .description("모니터링 서버 API에 대한 연동 문서입니다")
                .license("Kim Geon Hee").licenseUrl("https://github.com/kgggh/monitoring").version("1").build();
    }
}
