package com.noloafing.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix ="spring.datasource")//和配置文件绑定
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

}
