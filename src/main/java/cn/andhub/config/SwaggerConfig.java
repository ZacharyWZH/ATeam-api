package cn.andhub.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger-ui的配置
 * @author Zachary
 * @date 2017/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket TokenApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("token")
                .genericModelSubstitutes(DeferredResult.class)
//                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
                .select()
                .paths(PathSelectors.any())//过滤的接口
                .build()
                .apiInfo(tokenApiInfo());
    }

//    @Bean
//    public Docket UserApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("user")
//                .genericModelSubstitutes(DeferredResult.class)
////                .genericModelSubstitutes(ResponseEntity.class)
//                .useDefaultResponseMessages(false)
//                .forCodeGeneration(true)
//                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起
//                .select()
//                .paths(or(regex("/*")))//过滤的接口
//                .build()
//                .apiInfo(userApiInfo());
//    }

    private ApiInfo tokenApiInfo() {
        return new ApiInfoBuilder()
                .title("Api文档")
                .description("。。。")
                .termsOfServiceUrl("http://blog.didispace.com/")
                .contact("Zachary")
                .version("1.0")
                .build();
    }

//    private ApiInfo userApiInfo() {
//        return new ApiInfoBuilder()
//                .title("用户Api")
//                .description("。。。")
//                .termsOfServiceUrl("http://blog.didispace.com/")
//                .contact("Zachary")
//                .version("1.0")
//                .build();
//    }

}

