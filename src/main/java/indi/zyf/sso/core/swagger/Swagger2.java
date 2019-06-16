package indi.zyf.sso.core.swagger;

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

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket api(){  
        ParameterBuilder tokenPar = new ParameterBuilder();  
        List<Parameter> pars = new ArrayList<Parameter>();  
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();  
        pars.add(tokenPar.build());  
        return new Docket(DocumentationType.SWAGGER_2)  
            .select()  
            //.apis(RequestHandlerSelectors.any()) 
            //.paths(PathSelectors.regex("/api/.*"))  
            .apis(RequestHandlerSelectors.basePackage("indi.zyf.sso.controller"))
            .paths(PathSelectors.any())
            .build()  
            .globalOperationParameters(pars)  
            .apiInfo(apiInfo());  
    }  
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RESTful APIs")
                .description("登录系统")
                .termsOfServiceUrl("http://localhost/sso")
                	//.termsOfServiceUrl("http://blog.didispace.com/")
                //.contact("测试使用")
                .version("1.0")
                .build();
    }

}