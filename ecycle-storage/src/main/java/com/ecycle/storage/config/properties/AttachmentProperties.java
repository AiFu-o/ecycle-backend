package com.ecycle.storage.config.properties;

import com.ecycle.storage.constant.FileDataStoreType;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wangweichen
 * @Date 2024/4/9
 * @Description 附件配置
 */
@Configuration
@RefreshScope
@Data
@ConfigurationProperties(prefix = "ecycle.attachment")
public class AttachmentProperties {

    /**
     * 最大上传数量
     */
    @Value("${ecycle.attachment.max-upload-count:10}")
    private int maxUploadCount = 10;

    /**
     * 最大文件大小(MB)
     */
    @Value("${ecycle.attachment.max-file-size:10}")
    private Long maxFileSize;

    /**
     * 支持上传的类型
     */
    private List<String> types;

    /**
     * 存储地址
     */
    private String path;

    /**
     * 存储方式
     */
    @Value("${ecycle.attachment.store-type:DISK}")
    private FileDataStoreType storeType = FileDataStoreType.DISK;

}
