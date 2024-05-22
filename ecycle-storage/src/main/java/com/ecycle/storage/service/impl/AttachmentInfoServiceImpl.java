package com.ecycle.storage.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.common.utils.CurrentUserInfoUtils;
import com.ecycle.storage.config.properties.AttachmentProperties;
import com.ecycle.storage.exception.AttachmentException;
import com.ecycle.storage.model.AttachmentInfo;
import com.ecycle.storage.service.AttachmentDataService;
import com.ecycle.storage.service.AttachmentInfoService;
import com.ecycle.storage.mapper.AttachmentInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author wangweichen
 * @description 针对表【ecycle_attachment_info(附件)】的数据库操作Service实现
 * @createDate 2024-04-09 09:21:43
 */
@Service
public class AttachmentInfoServiceImpl extends ServiceImpl<AttachmentInfoMapper, AttachmentInfo>
        implements AttachmentInfoService {

    @Resource
    private AttachmentProperties attachmentProperties;

    @Resource
    private AttachmentDataService attachmentDataService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID insertFile(MultipartFile file, UUID belongId, String category, Boolean anonymous) throws IOException {
        if (null == belongId){
            belongId = UUID.randomUUID();
        }
        if(StringUtils.isEmpty(file.getOriginalFilename())){
            throw new NullPointerException();
        }
        String fileType = getFileType(file.getOriginalFilename());
        checkFileUpload(file, fileType);

        UUID id = UUID.randomUUID();
        AttachmentInfo attachmentInfo = new AttachmentInfo();
        attachmentInfo.setId(id);
        attachmentInfo.setBelongId(belongId);
        attachmentInfo.setFileName(getFileName(file, anonymous, fileType));
        attachmentInfo.setFileType(fileType);
        attachmentInfo.setSize(file.getSize());

        UUID currentUserId = CurrentUserInfoUtils.getCurrentUserId();
        attachmentInfo.setCreatorId(currentUserId);
        if (StringUtils.isNotEmpty(category)) {
            attachmentInfo.setCategory(category);
        }
        String path = attachmentDataService.saveData(attachmentInfo, file.getInputStream());
        attachmentInfo.setAddress(path);

        save(attachmentInfo);
        return id;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UUID insertFiles(MultipartFile[] files, UUID belongId, String category, Boolean anonymous) throws IOException {
        if (null == belongId){
            belongId = UUID.randomUUID();
        }
        checkFilesUpload(files);
        for (MultipartFile file : files) {
            insertFile(file, belongId, category, anonymous);
        }
        return belongId;
    }

    @Override
    public InputStream getInputStream(AttachmentInfo attachmentInfo) {
        return attachmentDataService.getInputStream(attachmentInfo);
    }

    @Override
    public List<AttachmentInfo> findByBelongId(UUID belongId) {
        QueryChainWrapper<AttachmentInfo> queryMapper = query();
        queryMapper.eq("belong_id", belongId);
        return queryMapper.list();
    }

    @Override
    public List<AttachmentInfo> findByBelongIdAndCategory(UUID belongId, String category) {
        QueryChainWrapper<AttachmentInfo> queryMapper = query();
        queryMapper.eq("belong_id", belongId);
        queryMapper.eq("category", category);
        return queryMapper.list();
    }

    @Override
    public Boolean removeFile(UUID fileId) {
        AttachmentInfo info = getById(fileId);
        if(null == info){
            throw new AttachmentException("找不到文件");
        }
        removeById(info);
        attachmentDataService.delete(info);
        return true;
    }

    private String getFileName(MultipartFile file, Boolean anonymous, String fileType) {
        String fileName = "";
        if (anonymous) {
            fileName = UUID.randomUUID() + "." + fileType;
        } else {
            fileName = file.getOriginalFilename();
        }
        return fileName;
    }

    private void checkFilesUpload(MultipartFile[] files) {
        int maxUploadCount = attachmentProperties.getMaxUploadCount();
        if (files.length > maxUploadCount) {
            throw new AttachmentException("文件数量不能超过" + maxUploadCount);
        }
    }

    private void checkFileUpload(MultipartFile file, String fileType) {
        Long maxFileSize = attachmentProperties.getMaxFileSize();
        if (!attachmentProperties.getTypes().contains(fileType)) {
            throw new AttachmentException("不支持的文件类型");
        }
        if (file.getSize() > maxFileSize * 1024 * 1024) {
            throw new AttachmentException("单个文件大小不能超过" + maxFileSize + "MB");
        }
    }

    private String getFileType(String fileName) {
        String fileType = "";
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            fileType = fileName.substring(index, fileName.length());
            if (fileType.length() > 0) {
                fileType = fileType.substring(1);
            }
        }
        return fileType;
    }

}




