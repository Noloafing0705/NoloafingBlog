package com.noloafing.baiduAI;
 
import lombok.Data;

import java.util.List;
 
/**
 * 文本审核结果返回
 */
@Data
public class TextCheckReturn {
    private long log_id;//请求唯一id，用于问题定位
    private String conclusion;//审核结果，可取值：合规、不合规、疑似、审核失败
    private Integer conclusionType;//审核结果类型，可取值1.合规，2.不合规，3.疑似，4.审核失败
    private List<TextData> data;//不合规/疑似/命中白名单项详细信息
}