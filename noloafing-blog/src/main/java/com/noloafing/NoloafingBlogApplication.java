package com.noloafing;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.noloafing.mapper")
@EnableScheduling //开启定时任务注解
public class NoloafingBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoloafingBlogApplication.class,args);
    }
}
