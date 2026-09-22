package com.itheima.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

import java.io.FileInputStream;
import java.io.InputStream;

public class AliOssUtil {
    // ==========改1：==========
    private static final String ENDPOINT = "";
    // ==========改2：直接填RAM子账号AK，不要环境变量那套==========
    private static final String ACCESS_KEY_ID = "";
    private static final String ACCESS_KEY_SECRET = "";
    // ==========改4：region改为==========
    private static final String REGION = "";
    // ==========改3：换成你的bucket名字==========
    private static final String BUCKET_NAME = "";

    public static String uploadFile(String objectName, InputStream in) throws Exception {



        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        // ✅这里重载方法，直接传入AK，不再用EnvironmentVariableCredentialsProvider
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(ENDPOINT)
                .credentialsProvider(new DefaultCredentialProvider(ACCESS_KEY_ID, ACCESS_KEY_SECRET))
                .clientConfiguration(clientBuilderConfiguration)
                .region(REGION)
                .build();

        String url = "";

        try {
            String content = "Hello OSS，你好世界";
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, objectName, in);
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            //url组成：https://bucket名称.区域节点/objectName
            url = "https://"+BUCKET_NAME+"."+ENDPOINT.substring(ENDPOINT.lastIndexOf("/")+1)+"/"+objectName;
            System.out.println("上传成功！");
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        return url;
    }
}
