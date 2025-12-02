package com.fallensakura.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CommonService {

    /**
     * 上传图片到R2
     * @param file
     * @return URL
     * @throws IOException
     */
    String uploadImage(MultipartFile file) throws IOException ;
}
