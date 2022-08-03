package com.study.srb.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.study.srb.exception.BusinessException;
import com.study.srb.oss.service.FileService;
import com.study.srb.oss.uitl.OssProperties;
import com.study.srb.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String upload(InputStream inputStream, String module, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(OssProperties.ENDPOINT, OssProperties.KEY_ID, OssProperties.KEY_SECRET);
        // 如果存储空间不存在，那么先创建,并设置权限为公共读
        if (!ossClient.doesBucketExist(OssProperties.BUCKET_NAME)) {
            ossClient.createBucket(OssProperties.BUCKET_NAME);
            ossClient.setBucketAcl(OssProperties.BUCKET_NAME, CannedAccessControlList.PublicRead);
        }
        try {
            // 创建文件的路径
            // 定义目录结构为"/module/2021/02/27/uuid.csv"
            // module是传过来的。所以需要自己生成一下后面的路径
            // 构建日期路径
            String timeFolder = new DateTime().toString("/yyyy/MM/dd/");
            // 构建文件名
            fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));
            // 组装路径
            String path = module + timeFolder + fileName;
            // 创建PutObject请求。
            ossClient.putObject(OssProperties.BUCKET_NAME, path, inputStream);
            // 定义上传成功后的访问url
            String url = "https://" + OssProperties.BUCKET_NAME + "." + OssProperties.ENDPOINT + "/" + path;
            return url;
        } catch (OSSException oe) {
            log.info("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.info("Error Message:" + oe.getErrorMessage());
            log.info("Error Code:" + oe.getErrorCode());
            log.info("Request ID:" + oe.getRequestId());
            log.info("Host ID:" + oe.getHostId());
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, oe);
        } catch (ClientException ce) {
            log.info("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.info("Error Message:" + ce.getMessage());
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
