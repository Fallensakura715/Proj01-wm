package com.fallensakura.config;

import com.fallensakura.interceptor.JwtTokenAdminInterceptor;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns(
                        "/admin/employee/login",
                        "/admin/common/upload",
                        "/v3/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/admin/workspace/**"
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(DATE_TIME_FORMAT);
            builder.timeZone(TimeZone.getTimeZone("GMT+8"));
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        };
    }

//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        log.info("扩展消息转换器...");
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(Jackson2ObjectMapperBuilder.json()
//                .simpleDateFormat(DATE_TIME_FORMAT)
//                .timeZone(TimeZone.getTimeZone("GMT+8"))
//                .build());
//
//        converters.addLast(converter);
//    }

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