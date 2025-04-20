package com.wrp.blog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author wrp
 * @since 2024-09-15 12:22
 **/
@Component
@AllArgsConstructor
public class JsonUtils {

    private ObjectMapper objectMapper;

    /**
     * 将对象转为json字符串
     * @param data 数据对象
     * @return json字符串
     */
    public String string(@NotNull Object data) {
        if(data instanceof String str) {
            return str;
        }

        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将json字符串转为指定类型的对象
     * @param data json字符串
     * @param type 指定转换的对象类型
     * @return 指定类的对象
     * @param <T> 类型参数
     */
    public <T> T toBean(@NotNull String data, @NotNull Class<T> type) {
        try {
            return objectMapper.readValue(data, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
