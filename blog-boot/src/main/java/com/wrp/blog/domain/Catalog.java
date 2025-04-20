package com.wrp.blog.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wrp
 * @since 2025-04-02 20:54
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "public.catalog", autoResultMap = true)
public class Catalog extends BaseEntity {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    private String catalogName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;
    private Integer articleCount;

    @TableField(exist = false)
    private List<Catalog> child;
}
