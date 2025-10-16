package com.fallensakura.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

//    /**
//     * 通过knife4j生成接口文档
//     * @return docket
//     */
//    @Bean
//    public Docket docket() {
//        log.info("开始生成接口文档");
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title("外卖项目接口文档")
//                .description("外卖项目接口文档").
//                build();
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.fallensakura.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    };
//
//    /**
//     * 设置静态资源映射
//     * @param registry 资源处理器注册表
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info("开始设置静态资源映射");
//        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
}