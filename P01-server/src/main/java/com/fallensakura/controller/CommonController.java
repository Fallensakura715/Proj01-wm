package com.fallensakura.controller;

import com.fallensakura.result.Result;
import com.fallensakura.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/common")
@Tag(name = "通用接口")
@Slf4j
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "文件上传")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return Result.success(commonService.uploadImage(file));
        } catch (IOException e) {
            return Result.error(e.getMessage());
        }
    }
}
