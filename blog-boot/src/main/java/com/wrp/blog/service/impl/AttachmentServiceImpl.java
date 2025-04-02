package com.wrp.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrp.blog.common.UserHolder;
import com.wrp.blog.common.enums.AttachmentType;
import com.wrp.blog.common.enums.ResultType;
import com.wrp.blog.common.exception.BusinessException;
import com.wrp.blog.domain.Attachment;
import com.wrp.blog.mapper.AttachmentMapper;
import com.wrp.blog.service.AttachmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

/**
 * @author wrp
 * @since 2024-09-08 21:18
 **/
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    @Value("${file.directory}")
    private String fileDirectory;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long upload(MultipartFile file) {
        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        attachment.setUrl(path + File.separator + UUID.randomUUID());
        attachment.setFileSize(file.getSize());
        attachment.setFileType(AttachmentType.of(file.getContentType()));
        attachment.setUserId(UserHolder.getUser().getId());
        save(attachment);
        File dir = new File(fileDirectory + File.separator + path);
        if(!dir.exists() && !dir.mkdirs()) {
            throw BusinessException.of(ResultType.S_LACK_SOURCE);
        }
        File uploadedFile = new File(fileDirectory + File.separator + attachment.getUrl());
        try {
            file.transferTo(uploadedFile);
        } catch (Exception e) {
            throw BusinessException.of(ResultType.S_LACK_SOURCE);

        }
        return attachment.getId();
    }

    @Override
    public ResponseEntity<Resource> download(Long id) {
        Attachment attachment = getById(id);
        if(attachment == null || !Objects.equals(attachment.getUserId(), UserHolder.getUser().getId())) {
            throw BusinessException.of(ResultType.S_NOT_FOUND);
        }

        Path filePath = Paths.get(fileDirectory).resolve(attachment.getUrl()).normalize();
        Resource resource = new FileSystemResource(filePath);
        if (resource.exists()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" +
                            URLEncoder.encode(attachment.getFileName(), StandardCharsets.UTF_8))
                    .body(resource);
        }
        throw BusinessException.of(ResultType.S_NOT_FOUND);
    }
}
