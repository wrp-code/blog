package com.wrp.blog.util;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 封装redis工具
 * @author wrp
 * @since 2024-09-15 12:17
 **/
@Component
@AllArgsConstructor
public class RedisUtils {

    private RedisTemplate<String, Object> redisTemplate;
    private JsonUtils jsonUtils;

    /**
     * 缓存数据，统一存储为string
     * @param key key
     * @param value 值
     */
    public void set(@NotEmpty String key, @NotNull Object value) {
        if(value.getClass().isAssignableFrom(String.class)){
            redisTemplate.opsForValue().set(key, value);
        }
        redisTemplate.opsForValue().set(key, jsonUtils.string(value));
    }

    /**
     * 直接获取数据
     * @param key key
     * @return 值
     */
    public String get(@NotEmpty String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取数据，并返回指定数据类型
     * @param key key
     * @param type 数据类型
     * @return 数据
     * @param <T> 参数类型
     */
    public <T> T get(@NotEmpty String key, @NotNull Class<T> type) {
        Object data = redisTemplate.opsForValue().get(key);
        if(data == null) {
            return null;
        }
        return jsonUtils.toBean(data.toString(), type);
    }
}
