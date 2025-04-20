package com.wrp.blog.typeHandler;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author wrp
 * @since 2024-09-08 21:14
 **/
public class StringListTypeHandler  extends ListTypeHandler<String> {

    @Override
    protected TypeReference<List<String>> specificType() {
        return new TypeReference<>() {};
    }
}