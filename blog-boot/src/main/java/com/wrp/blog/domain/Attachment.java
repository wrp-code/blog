package com.wrp.blog.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wrp.blog.common.enums.AttachmentType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 附件
 * @author wrp
 * @since 2024-09-08 18:52
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "public.attachment", autoResultMap = true)
public class Attachment extends BaseEntity {
    private Long userId;
    private String fileName;
    private AttachmentType fileType;
    private String url;
    private Long fileSize;

}
