package com.apocalypse.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail kaihuijing@gmail.com
 */
@EnableDiscoveryClient
@MapperScan(basePackages = "com.apocalypse.example.mapper")
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class}, scanBasePackages = {"com.apocalypse.example",
        "com.apocalypse.common.aspect"})
public class ExampleApplication {


    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

}
