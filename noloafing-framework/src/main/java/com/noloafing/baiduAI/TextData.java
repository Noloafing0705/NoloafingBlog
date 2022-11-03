package com.noloafing.baiduAI;
 
import lombok.Data;

import java.util.List;
 
/**
 * 不合规/疑似/命中白名单项详细信息
 */
@Data
public class TextData {
    private String msg;//不合规项描述信息
    private List<Hits> hits;//命中关键词信息
}