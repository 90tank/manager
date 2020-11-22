package com.docsys.manager.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.docsys.manager.service.FileStorageService;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.uploadPath}")
    private String uploadPath;

    private static final String[] VALID_FILE_EXTENSIONS = {"jpg", "jpeg", "png"};

    @Override
    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mediaType = FileUtil.extName(originalFilename);
        if (!isValidExt(mediaType)) {
            log.error("不合法的文件类型, mediaType:{}", mediaType);
        }
        String storeName = UUID.randomUUID().toString() + "." + mediaType;
        String thisMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String path = File.separator + thisMonth + File.separator + storeName;
        String storePath = uploadPath + path;
        // 创建文件夹
        FileUtil.mkdir(uploadPath + File.separator + thisMonth);
        try {
            FileUtil.writeBytes(file.getBytes(), new File(storePath));
        } catch (IOException e) {
            log.error("文件存储失败", e);
        }

        return "/file" + path;
    }

    private boolean isValidExt(String mediaType) {
        if (StrUtil.isBlank(mediaType)) {
            return false;
        }
        for (String ext : VALID_FILE_EXTENSIONS) {
            if (ext.equalsIgnoreCase(mediaType)) {
                return true;
            }
        }
        return false;
    }
}
