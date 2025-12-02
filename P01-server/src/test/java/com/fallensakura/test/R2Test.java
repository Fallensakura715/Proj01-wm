package com.fallensakura.test;

import com.fallensakura.service.CommonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class R2Test {

    @Autowired
    private CommonService commonService;

    @Test
    public void testR2UploadIamge() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        String url = commonService.uploadImage(file);

//        assertNotNull(url);
//        assertTrue(url.contains("imgu. falnsakura.top"));
        System.out.println("上传成功，URL: " + url);
    }
}
