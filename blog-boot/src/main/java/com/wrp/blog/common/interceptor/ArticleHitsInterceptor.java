package com.wrp.blog.common.interceptor;

import com.wrp.blog.service.ArticleService;
import com.wrp.blog.util.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * @author wrp
 * @since 2025-04-20 08:22
 **/
@Component
@RequiredArgsConstructor
public class ArticleHitsInterceptor implements HandlerInterceptor {

    final RedisTemplate<String, Object> redisTemplate;
    final ArticleService articleService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // regular：一个ip一天看同一个文章，只算一次观看记录
        String host = IpUtils.realIP(request);
        Long id = (Long) request.getAttribute("id");
        String articleHitKey = host + ":" + id;
        // setnx
        if(redisTemplate.opsForValue().setIfAbsent(articleHitKey, "0", 24, TimeUnit.HOURS)) {
            articleService.incrementHits(id);
        }
    }

}
