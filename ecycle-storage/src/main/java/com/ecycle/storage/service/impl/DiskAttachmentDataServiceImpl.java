package com.ecycle.storage.service.impl;

import com.ecycle.storage.config.properties.AttachmentProperties;
import com.ecycle.storage.exception.AttachmentException;
import com.ecycle.storage.model.AttachmentInfo;
import com.ecycle.storage.service.AttachmentDataService;
import io.jsonwebtoken.impl.io.BytesInputStream;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;

/**
 * @author wangweichen
 * @Date 2024/4/12
 * @Description TODO
 */
@Service
@Log4j2
@ConditionalOnExpression("#{'DISK'.equals(environment.getProperty('ecycle.attachment.store-type'))}")
public class DiskAttachmentDataServiceImpl implements AttachmentDataService {

    @Resource
    private AttachmentProperties attachmentProperties;

    @Override
    public byte[] getData(AttachmentInfo file) {
        String address = file.getAddress();
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(address);
            byte[] temp = new byte[1024];
            int count = -1;
            while ((count = fileInputStream.read(temp)) > -1) {
                arrayOutputStream.write(temp, 0, count);
            }
        } catch (IOException e) {
            log.error("加载文件数据异常：{}", address, e);
            throw new AttachmentException("文件读取失败," + e.getMessage(), e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                log.error("关闭文件流异常：{}", address, e);
                throw new AttachmentException("关闭文件流失败," + e.getMessage(), e);
            }
        }
        return arrayOutputStream.toByteArray();
    }

    @Override
    public InputStream getInputStream(AttachmentInfo file) {
        String address = file.getAddress();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(new File(address));
        } catch (FileNotFoundException e) {
            log.error("找不到文件：{}", address, e);
            throw new AttachmentException("找不到文件," + e.getMessage(), e);
        }
        return fileInputStream;
    }

    @Override
    public String saveData(AttachmentInfo file, byte[] content) {
        return this.saveData(file, new ByteArrayInputStream(content));
    }

    @Override
    public String saveData(AttachmentInfo file, InputStream inputStream) {
        String address = generateFileAddress(file);
        File saveFile = new File(address);
        if (!saveFile.exists()) {
            if (!saveFile.getParentFile().exists()) {
                if (!saveFile.getParentFile().mkdirs()) {
                    // 如果目录创建失败，检测一下目录是否已被其它线程创建成功如果已经创建则开始文件存储
                    if (!saveFile.getParentFile().exists()) {
                        log.error("创建目录异常：" + saveFile.getParentFile().getPath());
                        throw new AttachmentException("创建目录失败");
                    }
                }
            }
            FileOutputStream fileOutputStream = null;
            try {
                saveFile.createNewFile();
                fileOutputStream = new FileOutputStream(saveFile);
                byte[] temp = new byte[1024];
                int count = -1;
                while ((count = inputStream.read(temp)) > -1) {
                    fileOutputStream.write(temp, 0, count);
                }
            } catch (IOException e) {
                log.error("创建文件异常：{}", address, e);
                throw new AttachmentException("创建文件失败," + e.getMessage(), e);
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        log.error("关闭流异常：{}", address, e);
                        throw new AttachmentException("关闭文件流失败," + e.getMessage(), e);
                    }
                }
            }
        }
        return address;
    }

    private String getTempPath(AttachmentInfo attachmentInfo) {
        return attachmentProperties.getPath() + "/temp/" + attachmentInfo.getId() + ".delete";
    }

    @Override
    public void delete(AttachmentInfo file) {
        File delFile = new File(file.getAddress());
        File fileDelete = new File(getTempPath(file));
        if (!fileDelete.getParentFile().exists()) {
            if (!fileDelete.getParentFile().mkdirs()) {
                log.error("创建临时目录出错：{}", fileDelete.getParentFile().getPath());
                throw new AttachmentException("创建临时目录失败");
            }
        }
        if (!delFile.renameTo(fileDelete)) {
            log.error("移动文件出错：{}", fileDelete.getParentFile().getPath());
            throw new AttachmentException("移动文件失败");
        }

        String[] children = delFile.getParentFile().list();
        if (children == null || children.length == 0) {
            delFile.getParentFile().delete();
        }
    }

    @Override
    public void copy(AttachmentInfo sourceFile, AttachmentInfo targetFile) {

    }

    private String generateFileAddress(AttachmentInfo file) {
        String address = attachmentProperties.getPath();
        if (StringUtils.isNotEmpty(file.getCategory())) {
            address += "/" + file.getCategory().trim();
        } else {
            address += "/default";
        }
        address += "/" + file.getBelongId() + "/" + file.getFileName();
        return address;
    }

}
