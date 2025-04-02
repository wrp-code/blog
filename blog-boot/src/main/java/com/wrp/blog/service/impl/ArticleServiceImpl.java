package com.wrp.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrp.blog.common.UserHolder;
import com.wrp.blog.common.enums.ResultType;
import com.wrp.blog.common.exception.BusinessException;
import com.wrp.blog.controller.support.AddArticleParam;
import com.wrp.blog.controller.support.UpdateArticleParam;
import com.wrp.blog.domain.Article;
import com.wrp.blog.domain.User;
import com.wrp.blog.mapper.ArticleMapper;
import com.wrp.blog.service.ArticleService;
import com.wrp.blog.vo.ArticleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author wrp
 * @since 2024-09-08 21:17
 **/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {

    @Override
    public Long addArticle(AddArticleParam param) {
        User user = UserHolder.getUser();
        Article article = new Article()
                .setTitle(param.getTitle())
                .setContent(param.getContent())
                .setCatalogId(param.getCatalogId());
        article.setUserId(user.getId());
        save(article);
        return article.getId();
    }

    @Override
    public void deleteArticle(Long id) {
        getArticleWithCheck(id);
        removeById(id);
    }

    @Override
    public Long updateArticle(UpdateArticleParam param) {
        Article article = getArticleWithCheck(param.getId());
        article.setTitle(param.getTitle())
                .setContent(param.getContent());
        return article.getId();
    }

    private Article getArticleWithCheck(Long id) {
        Article article = getById(id);
        if(article == null) {
            throw BusinessException.of(ResultType.S_NOT_FOUND);
        }
        if(Objects.equals(article.getId(), UserHolder.getUser().getId())) {
            throw BusinessException.of(ResultType.U_INVALID_OPERATION);
        }
        return article;
    }

    @Override
    public IPage<ArticleVo> pageArticle(Long pageNo, Long pageSize, String keyword) {
        return doPage(pageNo, pageSize, keyword, false);
    }

    @Override
    public IPage<ArticleVo> selfArticle(Long pageNo, Long pageSize, String keyword) {
        return doPage(pageNo, pageSize, keyword, true);
    }

    private IPage<ArticleVo> doPage(Long pageNo, Long pageSize, String keyword, boolean self) {
        IPage<Article> page = new Page<>(pageNo, pageSize);
        page(page, new LambdaQueryWrapper<Article>()
                .eq(self, Article::getUserId, UserHolder.getUser().getId())
                .like(StringUtils.hasText(keyword), Article::getTitle, keyword));
        IPage<ArticleVo> result = new Page<>();
        BeanUtils.copyProperties(page, result);
        result.setRecords(page.getRecords()
                .stream()
                .map(a-> {
                    ArticleVo vo = new ArticleVo();
                    BeanUtils.copyProperties(a, vo);
                    return vo;
        }).toList());
        return result;
    }

    @Override
    public ArticleVo detail(Long id) {
        Article article = getById(id);
        if(article == null){
            throw BusinessException.of(ResultType.S_NOT_FOUND);
        }

        ArticleVo vo = new ArticleVo();
        BeanUtils.copyProperties(article, vo);
        return vo;
    }
}
