package com.ecycle.storage.service;

import com.ecycle.storage.model.AttachmentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
     * @return 文件 id
     * @throws IOException 如果在向文件中插入数据时发生I/O错误，则抛出IOException
     */
    List<UUID> insertFiles(MultipartFile[] files, UUID belongId, String category, Boolean anonymous) throws IOException;


    /**
     * 获取文件流
     * @param attachmentInfo 文件
     * @return 文件流
     */
    InputStream getInputStream(AttachmentInfo attachmentInfo);

    /**
     * 根据 belongId 查询文件
     * @param belongId belongId
     * @return 文件
     */
    List<AttachmentInfo> findByBelongId(UUID belongId);

    /**
     * 根据 belongId和分类 查询文件
     * @param belongId belongId
     * @param category 分类
     * @return 文件
     */
    List<AttachmentInfo> findByBelongIdAndCategory(UUID belongId, String category);

    /**
     * 删除文件
     * @param fileId
     * @return 是否删除成功
     */
    Boolean removeFile(UUID fileId);
}
