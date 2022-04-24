package com.study.springboot2study.thymeleafController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName FormController
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2022/4/21
 * @Version 1.0
 */
@Controller
@Slf4j
public class FormController {
    @GetMapping("/formLayouts")
    public String formLayouts() {
        return "form/form_layouts";
    }

    /**
     * MultipartFile 自动封装上传来的文件
     *
     * @param email
     * @param username
     * @param headerImg
     * @param photos
     * @return
     */
    @PostMapping("/upload")
    public String upload(String email,
                         String username,
                         // @RequestPart("headerImg") MultipartFile headerImg,
                         // @RequestPart("photos") MultipartFile[] photos

                         // 尝试不写注释（结果也可以）
                         MultipartFile headerImg,
                         MultipartFile[] photos) throws IOException {
        log.info("上传信息：email={},username={},headerImgSize={},photosNum={}",
                email, username, headerImg.getSize(), photos.length);


        String path1 = "D:\\javaTest\\headerImg\\";
        File dir1 = new File(path1);
        if (!dir1.exists()) {
            dir1.mkdirs();
        }

        String path2 = "D:\\javaTest\\photos\\";
        File dir2 = new File(path2);
        if (!dir2.exists()) {
            dir2.mkdirs();
        }
        if (!headerImg.isEmpty()) {
            // 保存到文件服务器
            headerImg.transferTo(new File("D:\\javaTest\\headerImg\\" + headerImg.getOriginalFilename()));
        }

        for (MultipartFile file : photos) {
            if (!file.isEmpty()) {
                file.transferTo(new File("D:\\javaTest\\photos\\" + file.getOriginalFilename()));
            }
        }

        return "main";
    }
}
