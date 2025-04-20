package com.wrp.blog.controller.support;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author wrp
 * @since 2025-04-20 10:37
 **/
@Data
public class UpdateCatalogName {
    @NotNull(message = "分类不能为空")
    private Long id;
    @NotNull(message = "分类名称不能为空")
    private String name;
}
