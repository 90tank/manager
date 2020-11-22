package com.docsys.manager.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.docsys.manager.service.FileStorageService;
import com.docsys.manager.util.ImageUtils;

import lombok.AllArgsConstructor;

/**
 * 文件存储
 */
@RestController
@RequestMapping(value = "/storage")
@AllArgsConstructor
public class FileStorageController {

	private final FileStorageService fileStorageService;

	@PostMapping(value = "/smallFile/write")
	public JSONObject upload(@RequestParam("data") MultipartFile file, HttpServletResponse response) {
		String url = fileStorageService.uploadFile(file);
		JSONObject json = new JSONObject();
		json.put("fileURL", url);
		return json;
	}

	@RequestMapping(value = "/file-to-stream", method = RequestMethod.GET)
	public Object fileToStream(HttpServletRequest req) throws IOException {

		String fileurl = "D:\\log\\202011\\6e9ce778-ad05-4f72-9107-2fdc205e08fd.JPG";

		byte[] aa = ImageUtils.bytesImage(fileurl);
		
		HttpHeaders responseHeaders = new HttpHeaders();
		
		responseHeaders.set("Content-Type", MediaType.IMAGE_PNG_VALUE);
		
		
		return  new ResponseEntity<byte[]>(aa, responseHeaders, HttpStatus.OK);
	}	 
}
