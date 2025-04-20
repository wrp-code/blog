package com.wrp.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wrp.blog.domain.Attachment;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wrp
 * @since 2024-09-08 20:21
 **/
public interface AttachmentService extends IService<Attachment> {

    /**
     * 上传文件
     * @param file 文件
     * @return 文件url
     */
    Long upload(MultipartFile file);

    /**
     * 下载文件
     * @param id 文件id
     * @return 文件流
     */
    ResponseEntity<Resource> download(Long id);
}
