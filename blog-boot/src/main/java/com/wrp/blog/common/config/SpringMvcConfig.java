package com.wrp.blog.common.config;

import com.wrp.blog.common.interceptor.ArticleHitsInterceptor;
import com.wrp.blog.common.interceptor.AuthInterceptor;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wrp
 * @since 2024-10-28 21:48
 **/
@Configuration
@RequiredArgsConstructor
public class SpringMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    final ArticleHitsInterceptor articleHitsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns("/**");

        registry.addInterceptor(articleHitsInterceptor)
                .addPathPatterns("/article/{id}");
    }
}
