package com.wrp.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrp.blog.common.UserHolder;
import com.wrp.blog.controller.support.CatalogParam;
import com.wrp.blog.controller.support.UpdateCatalogName;
import com.wrp.blog.domain.Catalog;
import com.wrp.blog.domain.User;
import com.wrp.blog.mapper.CatalogMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wrp
 * @since 2025-04-02 20:59
 **/
@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, Catalog>
    implements CatalogService {

    @Override
    public Long add(CatalogParam catalogParam) {
        Catalog catalog = new Catalog();
        catalog.setCatalogName(catalogParam.getCatalogName());
        catalog.setParentId(catalogParam.getParentId());
        catalog.setUserId(UserHolder.getUser().getId());
        save(catalog);
        return catalog.getId();
    }

    @Override
    public List<Catalog> tree() {
        User user = UserHolder.getUser();
        List<Catalog> list = list(new LambdaQueryWrapper<Catalog>()
                .eq(Catalog::getUserId, user.getId()));
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        Map<Long, Catalog> id2EntityMap = list.stream().collect(Collectors.toMap(Catalog::getId, c -> c));
        List<Catalog> roots = new LinkedList<>();
        list.forEach(catalog -> {
            if(catalog.getParentId() != null && id2EntityMap.containsKey(catalog.getParentId())) {
                Catalog parent = id2EntityMap.get(catalog.getParentId());
                if(CollectionUtils.isEmpty(parent.getChild())) {
                    parent.setChild(new ArrayList<>());
                }
                parent.getChild().add(catalog);
            } else {
                roots.add(catalog);
            }
        });

        return roots;
    }

    @Override
    public void updateName(UpdateCatalogName updateCatalogName) {
        Catalog catalog = getById(updateCatalogName.getId());
        if(catalog != null && UserHolder.getUser().getId().equals(catalog.getUserId())) {
            catalog.setCatalogName(updateCatalogName.getName());
            updateById(catalog);
        }
    }
}
