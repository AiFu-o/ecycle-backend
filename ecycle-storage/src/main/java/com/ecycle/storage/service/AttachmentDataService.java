package com.ecycle.storage.service;

import com.ecycle.storage.model.AttachmentInfo;

import java.io.InputStream;

/**
 * @author wangweichen
 * @description 文件存储实现
 * @createDate 2024-04-09 09:21:43
 */
public interface AttachmentDataService {

    /**
     * 获取文件(返回 byte 数组)
     *
     * @param file 文件
     * @return 文件 byte 数组
     */
    byte[] getData(AttachmentInfo file);

    /**
     * 获取文件(返回文件流)
     *
     * @param file 文件
     * @return 文件流
     */
    InputStream getInputStream(AttachmentInfo file);

    /**
     * 通过 byte 数组保存文件
     *
     * @param file 文件
     * @param content 文件内容
     * @return 存储位置
     */
    String saveData(AttachmentInfo file, byte[] content);

    /**
     * 通过文件流保存文件
     *
     * @param file 文件
     * @param inputStream 文件流
     * @return 存储位置
     */
    String saveData(AttachmentInfo file, InputStream inputStream);

    /**
     * 删除文件
     *
     * @param file 文件
     */
    void delete(AttachmentInfo file);

    /**
     * 复制文件到另一个文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     */
    void copy(AttachmentInfo sourceFile, AttachmentInfo targetFile);

}
