package com.apocalypse.example;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail kaihuijing@gmail.com
 */
@MapperScan(basePackages = "com.apocalypse.example.mapper")
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class}, scanBasePackages = {"com.apocalypse.example",
        "com.apocalypse.common.aspect"})
public class ExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

}
