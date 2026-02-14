package com.qingyunyouxiao.fsld.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Objects;

@Configuration
public class LocaleConfig {
   
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }   
   
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
        
            @Override
            public void addInterceptors(@NonNull InterceptorRegistry registry) {
                LocaleChangeInterceptor localeChangeInterceptor = localeChangeInterceptor();

                HandlerInterceptor nonNullInterceptor = Objects.requireNonNull(
                    (HandlerInterceptor) localeChangeInterceptor, "LocaleChangeInterceptor must not be null"
                );
                registry.addInterceptor(nonNullInterceptor);
            }
        };
    }  
}
