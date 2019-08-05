package com.apocalypse.example.controller.io;

import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.util.HttpContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/5
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/nio")
@Api(value = "NIO案例", tags = {"NIO案例"}, consumes = "application/json")
public class NioController {

    @PostMapping("/file/read")
    @ApiOperation(value = "文件读取", notes = "读取当前class", produces = "application/json")
    public Rest<String> enumPostValidate() throws Exception {
        String classDirPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        String classPath = StrUtil.replace(getClass().getName(), ".", "/");
        String filePath = classDirPath + classPath + ".class";
        String data = StrUtil.str(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        return Rest.ok(data);
    }

}
