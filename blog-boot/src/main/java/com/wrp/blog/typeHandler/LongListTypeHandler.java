package com.wrp.blog.typeHandler;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author wrp
 * @since 2024-09-08 21:14
 **/
public class LongListTypeHandler extends ListTypeHandler<Long> {

    @Override
    protected TypeReference<List<Long>> specificType() {
        return new TypeReference<>() {};
    }
}