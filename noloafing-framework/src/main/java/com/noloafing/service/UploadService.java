package com.noloafing.service;


import com.noloafing.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;


public interface UploadService {
    ResponseResult uploadImg(MultipartFile image);
}
