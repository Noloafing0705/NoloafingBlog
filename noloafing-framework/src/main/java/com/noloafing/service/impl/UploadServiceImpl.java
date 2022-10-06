package com.noloafing.service.impl;

import com.google.gson.Gson;
import com.noloafing.domain.ResponseResult;
import com.noloafing.enums.AppHttpCodeEnum;
import com.noloafing.exception.SystemException;
import com.noloafing.service.UploadService;
import com.noloafing.utils.FilePathUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;


@Service
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile image) {
        //获取原文件名
        String filename = image.getOriginalFilename().toLowerCase();
        if (!filename.endsWith(".png") && !filename.endsWith(".jpg")&&!filename.endsWith(".jpeg")) {
            throw new SystemException(AppHttpCodeEnum.UPLOAD_TYPE_ERROR);
        }
        String filePath = FilePathUtils.getFilePath(filename);
        String url = uploadFile(image,filePath);
        return ResponseResult.okResult(url);
    }
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String url;

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String uploadFile(MultipartFile image, String filePath) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            InputStream inputStream = image.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, filePath, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                //返回上传成功后的图片外链地址
                return url+filePath;
                //System.out.println(putRet.key);
                //System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                    throw new SystemException(AppHttpCodeEnum.UPLOAD_FAIL);
                }
            }
        } catch (Exception ex) {
            //ignore
            throw new SystemException(AppHttpCodeEnum.UPLOAD_FAIL);
        }
        return null;
    }
}
