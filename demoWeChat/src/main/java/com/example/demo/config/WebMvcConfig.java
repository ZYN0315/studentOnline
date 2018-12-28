package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by michaelod on 18-10-4.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * @param registry
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(myHandlerInterceptor())
//                .addPathPatterns("brary/**", "/academic/**","/user/update")
//                .excludePathPatterns("/**");
//    }
//
//    /***
//     * MyHandlerInterceptor自己定义的拦截器
//
//     */
//    @Bean
//    public MyHandlerInterceptor myHandlerInterceptor() {
//
//        return new MyHandlerInterceptor();
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH");
    }
}
