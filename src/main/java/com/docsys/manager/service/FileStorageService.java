package com.docsys.manager.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    /**
     * 文件上传到存储服务
     *
     * @param file 文件流
     * @return
     * @throws Exception
     */
    String uploadFile(MultipartFile file);
}
