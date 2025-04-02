package com.wrp.blog.controller.support;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wrp
 * @since 2024-09-08 20:24
 **/
@Data
public class AddArticleParam {
    @NotEmpty(message = "文章标题不能为空")
    private String title;
    @NotEmpty(message = "文章正文不能为空")
    private String content;
    @NotNull(message = "分类不能为空")
    private Long catalogId;
}
