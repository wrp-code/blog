package com.wrp.blog.controller.support;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author wrp
 * @since 2024-09-08 21:30
 **/
@Data
public class AddCommentParam {
    @NotNull(message = "未指定文章")
    private Long articleId;
    @NotEmpty(message = "内容不能为空")
    @Length(max = 256)
    private String content;
}
