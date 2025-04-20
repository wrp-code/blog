package com.wrp.blog.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wrp
 * @since 2024-09-09 08:08
 **/
public class MyBeanUtils {

    /**
     * 赋值
     * @param source 源对象
     * @param target 目标对象
     * @throws BeansException
     */
    public static void copy(Object source, Object target) throws BeansException {
        BeanUtils.copyProperties(source, target);
    }

    /**
     * 赋值时忽略空字段
     * @param source 源对象
     * @param target 目标对象
     * @throws BeansException
     */
    public static void copyIgnoreNull(Object source, Object target) throws BeansException {
        BeanUtils.copyProperties(source, target, getNullFields(source));
    }

    private static String[] getNullFields(Object source) {
        List<String> nullFiledNames = new ArrayList<>();
        Class<?> type = source.getClass();
        // 包括父类继承下来的字段
        while(!type.equals(Object.class)) {
            Field[] declaredFields = type.getDeclaredFields();
            for (Field field : declaredFields) {
                ReflectionUtils.makeAccessible(field);
                try {
                    if(field.get(source) == null) {
                        nullFiledNames.add(field.getName());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            type = type.getSuperclass();
        }

        return nullFiledNames.toArray(new String[0]);
    }
}
