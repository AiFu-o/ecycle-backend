package com.ecycle.storage.web;

import com.ecycle.common.context.RestResponse;
import com.ecycle.storage.exception.AttachmentException;
import com.ecycle.storage.model.AttachmentInfo;
import com.ecycle.storage.service.AttachmentInfoService;
import com.ecycle.storage.utils.ContentTypeUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
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

    @RequestMapping(value = "/file/preview/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<org.springframework.core.io.Resource> preview(@PathVariable UUID fileId, HttpServletRequest request) {
        AttachmentInfo attachmentInfo = attachmentInfoService.getById(fileId);
        if (null == attachmentInfo) {
            throw new AttachmentException("找不到id" + fileId + "为的附件");
        }
        InputStream inputStream = attachmentInfoService.getInputStream(attachmentInfo);
        String fileName = encodeFileName(request, attachmentInfo.getFileName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("charset", "utf-8");
        headers.add("Content-Disposition", "inline;filename=\"" + fileName + "\"");
        org.springframework.core.io.Resource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok().headers(headers)
                .contentType(MediaType
                        .valueOf(ContentTypeUtils.getContentTypeByFileName(attachmentInfo.getFileName())))
                .body(resource);
    }

    private String encodeFileName(HttpServletRequest request, String infoFileName) {
        String fileName = null;
        fileName = new String(infoFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        return fileName;
    }

}
