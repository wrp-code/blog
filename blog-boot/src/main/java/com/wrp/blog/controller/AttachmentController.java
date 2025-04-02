package com.wrp.blog.controller;

import com.wrp.blog.common.result.Result;
import com.wrp.blog.common.result.ResultUtils;
import com.wrp.blog.service.AttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wrp
 * @since 2024-09-08 21:41
 **/
@RestController
@RequestMapping("attachment")
@AllArgsConstructor
public class AttachmentController {
    private AttachmentService attachmentService;

    @PostMapping("upload")
    public Result<String> upload(@RequestPart("file") MultipartFile file) {
        return ResultUtils.success("" + attachmentService.upload(file));
    }

    @GetMapping("download")
    public ResponseEntity<Resource> download(@RequestParam("id") Long id) {
        return attachmentService.download(id);
    }

}
