package com.apocalypse.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail kaihuijing@gmail.com
 */
@EnableCaching
@MapperScan(basePackages = "com.apocalypse.example.mapper")
@SpringBootApplication(scanBasePackages = {
        "com.apocalypse.example", "com.apocalypse.common.config",
        "com.apocalypse.common.util", "com.apocalypse.common.exception",
        "com.apocalypse.common.advice", "com.apocalypse.common.aspect",
        "com.apocalypse.common.interceptor",
}, exclude = JtaAutoConfiguration.class)
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.apocalypse"})
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

}
