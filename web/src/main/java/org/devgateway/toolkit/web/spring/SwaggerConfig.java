package org.devgateway.toolkit.web.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket ad3Api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("AD3 Api").apiInfo(ad3ApiInfo())
                .select().apis(RequestHandlerSelectors.any())
                .paths(regex("/data/.*"))
                .paths(regex("/files/.*"))
                .build();
    }

    @Bean
    public Docket manageApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("manageApi")
                .select().apis(RequestHandlerSelectors.any()).paths(regex("/manage/.*")).build();
    }

    private ApiInfo ad3ApiInfo() {
        return new ApiInfoBuilder().title("Application API")
                .description("These endpoints are used to get data from db").license("MIT License")
                .licenseUrl("https://opensource.org/licenses/MIT").version("1.0").build();
    }

}