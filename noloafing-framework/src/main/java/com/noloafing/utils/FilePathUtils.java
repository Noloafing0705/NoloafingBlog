package com.noloafing.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传路径生成工具类
 */
public class FilePathUtils {
    public static String getFilePath(String filename){
        //按日期格式确定当前日期 作为 文件目录
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = dateFormat.format(new Date());
        //以UUID作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //获取文件后缀并拼接后缀名
        int index = filename.lastIndexOf(".");
        String fileType = filename.substring(index);
        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }
}
