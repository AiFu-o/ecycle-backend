package com.ecycle.storage.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangweichen
 * @Date 2024/5/9
 * @Description TODO
 */
public class ContentTypeUtils {

    public static String getFileType(String fileName){
        String fileType = "";
        int index = fileName.lastIndexOf(".");
        if( index!= -1){
            fileType = fileName.substring(index, fileName.length());
            if(fileType.length() > 0){
                fileType = fileType.substring(1);
            }
        }
        return fileType;
    }

    private final static Map<String, String> HTTP_CONTENT_TYPE = new HashMap<String, String>();

    static{
        HTTP_CONTENT_TYPE.put("txt", "text/plain");
        HTTP_CONTENT_TYPE.put("doc", "application/msword");
        HTTP_CONTENT_TYPE.put("docx", "application/msword");
        HTTP_CONTENT_TYPE.put("xls", "application/vnd.ms-excel");
        HTTP_CONTENT_TYPE.put("xlsx", "application/vnd.ms-excel");
        HTTP_CONTENT_TYPE.put("ppt", "application/vnd.ms-powerpoint");
        HTTP_CONTENT_TYPE.put("pptx", "application/vnd.ms-powerpoint");
        HTTP_CONTENT_TYPE.put("pdf", "application/pdf");
        HTTP_CONTENT_TYPE.put("png", "image/png");
        HTTP_CONTENT_TYPE.put("gif", "image/gif");
        HTTP_CONTENT_TYPE.put("ico", "image/x-ico");
        HTTP_CONTENT_TYPE.put("jpeg", "image/jpeg");
        HTTP_CONTENT_TYPE.put("jpe", "image/jpeg");
        HTTP_CONTENT_TYPE.put("jpg", "image/jpeg");
    }

    public static String getContentTypeByFileName(String fileName){
        String contentType = HTTP_CONTENT_TYPE.get(getFileType(fileName));
        if(contentType == null){
            return "application/octet-stream";
        }
        return contentType;
    }

    public static String getContentType(String fileType){
        String contentType = HTTP_CONTENT_TYPE.get(fileType);
        if(contentType == null){
            return "application/octet-stream";
        }
        return contentType;
    }

}
