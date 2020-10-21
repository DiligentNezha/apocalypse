package com.apocalypse.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail kaihuijing@gmail.com
 */
@EnableAsync
@EnableCaching
@MapperScan(basePackages = "com.apocalypse.cms.mapper")
@SpringBootApplication(scanBasePackages = {
        "com.apocalypse.cms",
        "com.apocalypse.common.util.bean",
        "com.apocalypse.common.core.util",
        "com.apocalypse.common.core.convert",
        "com.apocalypse.common.data.mybatis.plugin",
        "com.apocalypse.common.boot.aspect",
        "com.apocalypse.common.boot.init",
        "com.apocalypse.common.boot.web.exception"
})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.apocalypse"})
public class CmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

}
