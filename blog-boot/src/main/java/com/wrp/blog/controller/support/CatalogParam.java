package com.wrp.blog.controller.support;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author wrp
 * @since 2025-04-02 21:02
 **/
@Data
public class CatalogParam {
    @NotEmpty(message = "分类名称不能为空")
    @Length(max = 16, message = "分类名称最长16字符")
    private String catalogName;
    private Long parentId;
}
