package com.wrp.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrp.blog.controller.support.CatalogParam;
import com.wrp.blog.controller.support.UpdateCatalogName;
import com.wrp.blog.domain.Catalog;

import java.util.List;

/**
 * @author wrp
 * @since 2025-04-02 20:59
 **/
public interface CatalogService extends IService<Catalog> {
    Long add(CatalogParam catalogParam);

    List<Catalog> tree();

    void updateName(UpdateCatalogName updateCatalogName);
}
