package com.wrp.blog;

import com.wrp.blog.localhost.Loader;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wrp
 * @since 2025-04-16 21:29
 **/
@SpringBootTest
public class LoadBlogs {

    @Resource
    Loader loader;

    @Test
    public void loads() {
        loader.load();
    }
}
