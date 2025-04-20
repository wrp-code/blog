package com.wrp.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wrp.blog.common.result.Result;
import com.wrp.blog.common.result.ResultUtils;
import com.wrp.blog.controller.support.AddArticleParam;
import com.wrp.blog.controller.support.UpdateArticleParam;
import com.wrp.blog.service.ArticleService;
import com.wrp.blog.vo.ArticleListVo;
import com.wrp.blog.vo.ArticleVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wrp
 * @since 2024-09-08 20:20
 **/
@RestController
@RequestMapping("article")
@AllArgsConstructor
public class ArticleController {

    private ArticleService articleService;

//    @PostMapping
//    public Result<String> addArticle(@RequestBody @Validated AddArticleParam param) {
//        return ResultUtils.success("" + articleService.addArticle(param));
//    }

    @GetMapping("{id}")
    public Result<ArticleVo> detail(@PathVariable("id") Long id, HttpServletRequest request) {
        request.setAttribute("id", id);
        return ResultUtils.success(articleService.detail(id));
    }

//    @DeleteMapping("{id}")
//    public Result<Void> deleteArticle(@PathVariable("id") Long id) {
//        articleService.deleteArticle(id);
//        return ResultUtils.success();
//    }
//
//    @PutMapping()
//    public Result<String> updateArticle(@Validated UpdateArticleParam param) {
//        return ResultUtils.success("" + articleService.updateArticle(param));
//    }

    @GetMapping("list")
    public Result<IPage<ArticleListVo>> page(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Long pageNo,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Long pageSize,
            @RequestParam(value = "keyword", required = false) String keyword) {
        IPage<ArticleListVo> page = articleService.pageArticle(pageNo, pageSize, keyword);
        return ResultUtils.success(page);
    }

    @GetMapping("top10")
    public Result<List<ArticleListVo>> top10() {
        return ResultUtils.success(articleService.top10());
    }
}
