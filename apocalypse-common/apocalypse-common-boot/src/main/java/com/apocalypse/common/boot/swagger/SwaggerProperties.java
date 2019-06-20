package com.apocalypse.common.boot.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/20
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {
    private String title;
    private String description;
    private String version;
    private String basePackage;
}
