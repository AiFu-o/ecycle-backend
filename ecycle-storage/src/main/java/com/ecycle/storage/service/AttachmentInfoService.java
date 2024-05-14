package com.ecycle.storage.service;

import com.ecycle.storage.model.AttachmentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
* @author wangweichen
* @description 针对表【ecycle_attachment_info(附件)】的数据库操作Service
* @createDate 2024-04-09 09:21:43
*/
public interface AttachmentInfoService extends IService<AttachmentInfo> {

    /**
     * 上传文件
     *
     * @param file 文件
     * @param belongId belongId
     * @param category 文件分组
     * @param anonymous 是否匿名
     * @return fileId
     * @throws IOException 如果在向文件中插入数据时发生I/O错误，则抛出IOException
     */
    UUID insertFile(MultipartFile file, UUID belongId, String category, Boolean anonymous) throws IOException;

    /**
     * 上传多个文件
     *
     * @param files 文件
     * @param belongId belongId
     * @param category 文件分组
     * @param anonymous 是否匿名
     * @return belongId
     * @throws IOException 如果在向文件中插入数据时发生I/O错误，则抛出IOException
     */
    UUID insertFiles(MultipartFile[] files, UUID belongId, String category, Boolean anonymous) throws IOException;


    /**
     * 获取文件流
     * @param attachmentInfo 文件
     * @return 文件流
     */
    InputStream getInputStream(AttachmentInfo attachmentInfo);
}
