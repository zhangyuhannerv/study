package com.study.srb.oss.controller.api;

import com.study.srb.exception.BusinessException;
import com.study.srb.oss.service.FileService;
import com.study.srb.result.R;
import com.study.srb.result.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Api("阿里云文件管理")
// 在gataway里配置了跨域，和@CrossRoigin冲突
// @CrossOrigin
@RestController
@RequestMapping("/api/oss/file")
public class FileController {
    @Resource
    private FileService fileService;


    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(@ApiParam(value = "文件", required = true)
                    @RequestParam("file") MultipartFile file,
                    @ApiParam(value = "模块", required = true)
                    @RequestParam("module") String module) {
        try {
            String url = fileService.upload(file.getInputStream(), module, file.getOriginalFilename());
            return R.ok().message("文件上传成功").data("url", url);
        } catch (BusinessException e) {
            throw e;
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("删除OSS文件")
    @DeleteMapping("/remove")
    public R remove(
            @ApiParam(value = "要删除的文件路径", required = true)
            @RequestParam("url") String url) {
        fileService.removeFile(url);
        return R.ok().message("删除成功");
    }
}
