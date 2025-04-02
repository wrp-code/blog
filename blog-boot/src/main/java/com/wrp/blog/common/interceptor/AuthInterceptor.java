package com.wrp.blog.common.interceptor;

import com.wrp.blog.common.UrlFilterProperties;
import com.wrp.blog.common.UserHolder;
import com.wrp.blog.common.enums.ResultType;
import com.wrp.blog.common.exception.BusinessException;
import com.wrp.blog.domain.User;
import com.wrp.blog.util.RedisUtils;
import com.wrp.blog.util.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author wrp
 * @since 2024-09-10 23:28
 **/
@Component
@Slf4j
@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private JwtUtils jwtUtils;
    private RedisUtils redisUtils;
    private UrlFilterProperties urlFilterProperties;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        // 判断当前拦截到的是Controller的方法还是其他资源
        if(!(handler instanceof HandlerMethod)){
            // 当前拦截到的不是动态方法，直接放行
            return true;
        }

        // 白名单直接放行
        if(whiteListContains(request)) {
            return true;
        }

        //从请求头或queryString中获取令牌
        String token = getToken(request);
        if(!StringUtils.hasText(token)) {
            throw BusinessException.of(ResultType.S_MISS_TOKEN);
        }
        //校验令牌
        String username;
        try{
            username = jwtUtils.parseJWT(token);
            User user = redisUtils.get(username, User.class);
            if(user == null) {
                throw BusinessException.of(ResultType.U_NO_LOGIN);
            }
            UserHolder.setUser(user);
        } catch (Exception e){
            throw BusinessException.of(ResultType.S_INVALID_TOKEN);
        }

        if(!StringUtils.hasText(username)){
            throw BusinessException.of(ResultType.S_INVALID_TOKEN);
        }

        return true;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(jwtUtils.getTokenName());
        if(StringUtils.hasText(token)){
            return token;
        }
        return request.getParameter(jwtUtils.getTokenName());
    }

    private boolean whiteListContains(HttpServletRequest request) {
        return urlFilterProperties
                .getWhiteList()
                .stream()
                .anyMatch(item->
                        item.method().equalsIgnoreCase(request.getMethod()) &&
                        item.url().equals(request.getRequestURI()));
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
