package com.wrp.blog.localhost;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wrp.blog.domain.Article;
import com.wrp.blog.service.ArticleService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author wrp
 * @since 2025-04-16 21:29
 **/
@Component
public class Loader {

    @Value("${load.path}")
    String loadPath;

    @Resource
    ArticleService articleService;

    public void load() {
        File file = FileUtil.file(loadPath);
        doLoad(file);

    }

    private void doLoad(File file) {
        if(file.isFile() && file.getName().endsWith(".md")) {
            saveArticle(file.getName().substring(0,file.getName().lastIndexOf(".md")), FileUtil.readString(file, StandardCharsets.UTF_8));
            return;
        }
        File[] files = file.listFiles();
        if(files == null) return;
        for (File value : files) {
            doLoad(value);
        }
    }

    private void saveArticle(String name, String content) {
        Article article = new Article();
        article.setUserId(1907428162994118658L);
        article.setUsername("wrp");
        article.setTitle(name);
        article.setContent(content);
        article.setCatalogId(1907430552837214309L);
        article.setHits(0L);
        Article dbArticle = articleService.getOne(new LambdaQueryWrapper<Article>().eq(Article::getTitle, name));
        if(dbArticle == null) {
            articleService.save(article);
        }else {
            BeanUtils.copyProperties(article, dbArticle);
            articleService.updateById(dbArticle);
        }
    }
}
