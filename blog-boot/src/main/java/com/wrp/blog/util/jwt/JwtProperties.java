package com.wrp.blog.util.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt相关的外部化配置
 * @author wrp
 * @since 2024-09-10 23:15
 **/
@Data
@Component
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
    private String secretKey;
    /**
     * 毫秒单位
     */
    private Long ttl;
    private String tokenName;
}
