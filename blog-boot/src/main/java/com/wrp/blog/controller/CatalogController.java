package com.wrp.blog.controller;

import com.wrp.blog.common.result.Result;
import com.wrp.blog.common.result.ResultUtils;
import com.wrp.blog.controller.support.CatalogParam;
import com.wrp.blog.controller.support.UpdateCatalogName;
import com.wrp.blog.domain.Catalog;
import com.wrp.blog.service.impl.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wrp
 * @since 2025-04-02 21:00
 **/
@RestController
@RequestMapping("catalog")
@RequiredArgsConstructor
public class CatalogController {

    final CatalogService catalogService;

    @PostMapping
    public Result<String> add(@RequestBody @Validated CatalogParam catalogParam) {
        return ResultUtils.success("" + catalogService.add(catalogParam));
    }

    @PutMapping
    public Result<Void> updateName(@RequestBody @Validated UpdateCatalogName updateCatalogName) {
        catalogService.updateName(updateCatalogName);
        return ResultUtils.success();
    }

    @GetMapping
    public Result<List<Catalog>> tree() {
        return ResultUtils.success(catalogService.tree());
    }
}
