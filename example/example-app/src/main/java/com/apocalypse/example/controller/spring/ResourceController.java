package com.apocalypse.example.controller.spring;

import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/resource")
@Api(value = "Resource", tags = {"Resource"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ResourceController {

    private static final String FILE_PATH =
            ResourceController.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1) +
                    "application.yml";

    @GetMapping("/loader")
    @ApiOperation(value = "DefaultResourceLoader", notes = "DefaultResourceLoader 测试", produces =
            MediaType.APPLICATION_JSON_VALUE)
    public Rest<BaseResponse> register() {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Map<String, String> map = new HashMap<>();

        Resource resource = resourceLoader.getResource(FILE_PATH);
        map.put(FILE_PATH, resource.getClass().getName());

        String location = "/application.yml";
        Resource resource1 = resourceLoader.getResource(location);
        map.put(location, resource1.getClass().getName());

        String newFilePath = "file" + FILE_PATH.substring(1);
        Resource resource2 = resourceLoader.getResource(newFilePath);
        map.put(newFilePath, resource2.getClass().getName());

        String httpUrl = "https://www.baidu.com";
        Resource resource3 = resourceLoader.getResource(httpUrl);
        map.put(httpUrl, resource3.getClass().getName());

        return Rest.vector("content", map, Map.class);
    }
}
