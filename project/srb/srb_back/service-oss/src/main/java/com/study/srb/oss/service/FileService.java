package com.study.srb.oss.service;

import java.io.InputStream;

public interface FileService {
    /**
     * 文件上传至阿里云
     *
     * @return 返回文件上传后的访问路径
     */
    String upload(InputStream inputStream, String module, String fileName);

    void removeFile(String url);
}
