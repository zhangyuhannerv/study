package com.study;

import io.minio.*;
import io.minio.errors.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FileUploader {
    public static final String URL = "http://localhost:9000";
    public static final String username = "gpRNbdaXtWHu5e1qPxZX";
    public static final String password = "zButGTb3inDwy3DvjQo2TgzLUkKshgdm2VAWtxzV";
    public static final String bucket = "test-bucket-1";

    public static final String path = "my-path1/";

    public static final String fileName = "picture1.png";
    public static final String localFile = "/Users/zhangyuhan/MyDocument/picture/wallhaven-j3evpy.png";

    public static final String downloadFile = "/Users/zhangyuhan/Downloads/1.png";

    public static void main(String[] args) {
        try {
            MinioClient minioClient = createMinioClient();
            createBucket(minioClient);

//            ObjectWriteResponse objectWriteResponse = uploadFile(minioClient);
//            System.out.println(objectWriteResponse.etag());
//            System.out.println(objectWriteResponse.versionId());
//            System.out.println("保存成功");


            // 不知道为什么不生效
//            DownloadObjectArgs downloadObjectArgs = downloadFile();
//            System.out.println(downloadObjectArgs.filename());
//            System.out.println(downloadObjectArgs.bucket());
//            System.out.println(downloadObjectArgs.region());
//            System.out.println("下载成功");

            // 因此建议用这个下载
            downloadFileByInputStream(minioClient);
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public static MinioClient createMinioClient() {
        return MinioClient.builder()
                .endpoint(URL)
                .credentials(username, password)
                .build();
    }

    public static void createBucket(MinioClient minioClient) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        } else {
            System.out.println("Bucket " + bucket + " already exists.");
        }
    }

    public static ObjectWriteResponse uploadFile(MinioClient minioClient) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucket)
                        .object(path + fileName)// 保存到minio里的路径和名称，可以不需要path，直接保存名称，此时文件在桶下面
                        .filename(localFile)// 本地磁盘的路径
                        .build());
    }

    public static DownloadObjectArgs downloadFile() {
        return DownloadObjectArgs.builder()
                .bucket(bucket)
                .object(path + fileName)
                .filename(downloadFile)
                .build();
    }

    public static InputStream getFileInputStream(MinioClient minioClient) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(path + fileName)
                .build());
    }

    public static void downloadFileByInputStream(MinioClient minioClient) {
        try (InputStream inputStream = getFileInputStream(minioClient);
             OutputStream outputStream = Files.newOutputStream(Paths.get(downloadFile))) {


            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            System.out.println("下载成功");

        } catch (Exception e) {
            throw new RuntimeException("下载文件失败", e);
        }
    }


}
