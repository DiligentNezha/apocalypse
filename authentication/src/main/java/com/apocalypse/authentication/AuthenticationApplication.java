package com.apocalypse.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/9/19
 */
@EnableCaching
@MapperScan(basePackages = "com.apocalypse.authentication.mapper")
@SpringBootApplication(scanBasePackages = {
        "com.apocalypse.authentication", "com.apocalypse.common.config",
        "com.apocalypse.common.util", "com.apocalypse.common.exception",
        "com.apocalypse.common.advice", "com.apocalypse.common.aspect",
        "com.apocalypse.common.interceptor"
})
public class AuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }
}
