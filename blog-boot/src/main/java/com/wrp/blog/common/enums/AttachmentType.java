package com.wrp.blog.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wrp.blog.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.MediaType;

/**
 * @author wrp
 * @since 2024-10-28 21:59
 **/
@Getter
@AllArgsConstructor
public enum AttachmentType {
    JPG(1, "JPG"),
    PNG(2, "PNG"),
    ;
    @JsonValue
    @EnumValue
    private final int code;
    private final String name;

    public static AttachmentType of(String contentType) {
        if(MediaType.IMAGE_JPEG_VALUE.equals(contentType)) {
            return JPG;
        } else if(MediaType.IMAGE_PNG_VALUE.equals(contentType)) {
            return PNG;
        } else {
            throw BusinessException.of(ResultType.S_FILE_TYPE_NOT_SUPPORT);
        }
    }
}
