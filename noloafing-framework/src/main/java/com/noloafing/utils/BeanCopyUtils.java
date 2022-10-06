package com.noloafing.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现Vo数据封装的工具类
 */
public class BeanCopyUtils {
    /**
     * 用于单个实体类VO优化
     */
    public static   <T> T copyBean(Object source,Class<T> clazz){
        T result =null;
        try {
            result = clazz.newInstance();
            //copy属性
            BeanUtils.copyProperties(source,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用于链表类型实体类VO优化
     */
    public static <O,T> List<T> copyBeanList(List<O> source,Class<T> clazz){
        return source.stream()
                .map(O->BeanCopyUtils.copyBean(O, clazz))
                .collect(Collectors.toList());
    }
}
