package com.wrp.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wrp.blog.domain.Article;
import com.wrp.blog.vo.ArticleListVo;
import com.wrp.blog.vo.ArticleVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wrp
 * @since 2024-09-08 19:04
 **/
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("""
        select id,create_time,username,title,hits from public.article 
        WHERE deleted=0 order by hits desc,create_time desc limit 10;
    """)
    List<ArticleListVo> top10();
}
