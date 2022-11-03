package com.noloafing.baiduAI;
 
import lombok.Data;

import java.util.List;
 
/**
 * 命中关键词信息
 */
@Data
public class Hits {
    private String datasetName;//违规项目所属数据集名称
    private List<String> words;//违规文本关键字
}