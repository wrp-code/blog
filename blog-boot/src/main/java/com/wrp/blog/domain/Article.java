package com.wrp.blog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文章
 * @author  wrp
 * @since  2024-09-08 18:34
 **/
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "public.article", autoResultMap = true)
public class Article extends BaseEntity {
    private String title;
    private String content;
    private Long userId;
    private String username;
    private Long catalogId;
    private Long hits;
}
