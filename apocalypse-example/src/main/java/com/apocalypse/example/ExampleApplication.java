package com.apocalypse.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail kaihuijing@gmail.com
 */
@MapperScan(basePackages = "com.apocalypse.example.mapper")
@SpringBootApplication(scanBasePackages = {
        "com.apocalypse.example", "com.apocalypse.common.config",
        "com.apocalypse.common.util", "com.apocalypse.common.exception",
        "com.apocalypse.common.advice", "com.apocalypse.common.aspect",
        "com.apocalypse.common.interceptor",
})
public class ExampleApplication {


    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

}
