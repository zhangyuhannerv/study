package com.study.aliyunoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.AccessControlList;
import com.aliyun.oss.model.CannedAccessControlList;
import org.junit.Test;

public class OssTest {
    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    String endpoint = "oss-cn-qingdao.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    String accessKeyId = "";
    String accessKeySecret = "";
    String bucketName = "srb-file-zhangyuhan-1";

    /**
     * 测试创建存储空间
     */
    @Test
    public void testCreateBucket() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 创建存储空间
        ossClient.createBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    public void getBucketAlc() {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 获取存储空间的访问权限
        AccessControlList bucketAcl = ossClient.getBucketAcl(bucketName);
        System.out.println(bucketAcl.toString());
        ossClient.shutdown();
    }

    @Test
    public void setBucketAcl() {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置存储空间的访问权限
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        ossClient.shutdown();
    }

    @Test
    public void doesBucketExist() {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        System.out.println(ossClient.doesBucketExist(bucketName));

        ossClient.shutdown();
    }
}
