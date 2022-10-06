package com.noloafing;

import com.github.jeffreyning.mybatisplus.conf.EnableMPP;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.noloafing.mapper")
@EnableTransactionManagement
@EnableMPP
public class NoloafingAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoloafingAdminApplication.class, args);
    }
}
