package com.roilka.roilka.question.api.config;/**
 * Package: com.roilka.roilka.question.api.config
 * <p>
 * Description： TODO
 * <p>
 * Author: zhanghui
 * <p>
 * Date: Created in 2019/12/1 0:14
 * <p>
 * Company: tuhu
 * <p>
 * Copyright: Copyright (c) 2019
 * <p>
 * Modified By:
 */

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanghui
 * @description swagger配置
 * @date 2019/12/1
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "question",name = "swagger-open",havingValue = "true")
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.roilka"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(parameterBuilder());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("东望长右")
                .description("东望长右")
                .termsOfServiceUrl("https://www.cnblogs.com/Roilka/")
                .contact("Roilka")
                .version("1.0").license("证书").licenseUrl("证书地址")
                .build();
    }

    private List<Parameter> parameterBuilder() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("测试a").description("这是测试一下").modelRef(new ModelRef("不知道干啥的")).allowMultiple(false).defaultValue("test").required(false).order(0).parameterType("head").build();
        List<Parameter> par = new ArrayList<>();
        par.add(parameterBuilder.build());
        return par;
    }
}
