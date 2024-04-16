package com.ecycle.storage.web;

import com.ecycle.common.context.RestResponse;
import com.ecycle.storage.config.properties.AttachmentProperties;
import com.ecycle.storage.exception.AttachmentException;
import com.ecycle.storage.service.AttachmentInfoService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wangweichen
 * @Date 2024/4/9
 * @Description 附件上传接口
 */
@RestController
public class AttachmentController {

    @Resource
    private AttachmentInfoService attachmentInfoService;

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public RestResponse<UUID> uploadFile(@RequestParam(name = "belongId", required = false) UUID belongId,
                                           @RequestParam(name = "category", required = false) String category,
                                           @RequestParam("file") MultipartFile file,
                                           @RequestParam(name = "anonymous", defaultValue = "false") Boolean anonymous) {
        UUID fileId = null;
        try {
            fileId = attachmentInfoService.insertFile(file, belongId, category, anonymous);
        } catch (AttachmentException e) {
            e.printStackTrace();
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return RestResponse.success(fileId);
    }

    @RequestMapping(value = "uploadFiles", method = RequestMethod.POST)
    public RestResponse<UUID> uploadFiles(@RequestParam(name = "belongId", required = false) UUID belongId,
                                            @RequestParam(name = "category", required = false) String category,
                                            @RequestParam("files") MultipartFile[] files,
                                            @RequestParam(name = "anonymous", defaultValue = "false") Boolean anonymous) {
        try {
            belongId = attachmentInfoService.insertFiles(files, belongId, category, anonymous);
        } catch (AttachmentException e) {
            e.printStackTrace();
            return RestResponse.validfail(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return RestResponse.success(belongId);
    }
}
