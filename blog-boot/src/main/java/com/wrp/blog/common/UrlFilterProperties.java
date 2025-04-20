package com.wrp.blog.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wrp
 * @since 2024-10-09 08:19
 **/
@Data
@Component
@ConfigurationProperties(prefix = "url.filter")
public class UrlFilterProperties {
    private List<UrlItem> whiteList;

    public record UrlItem(String method, String url){}
}
