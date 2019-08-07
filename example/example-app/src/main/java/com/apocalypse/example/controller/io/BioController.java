package com.apocalypse.example.controller.io;

import com.apocalypse.common.dto.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/5
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/bio")
@Api(value = "BIO案例", tags = {"BIO案例"}, consumes = "application/json")
public class BioController {

    @PostMapping("/file/read")
    @ApiOperation(value = "文件读取", notes = "读取application.yml", produces = "application/json")
    public Rest<String> fileReader() throws Exception {
        String classDirPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        String filePath = classDirPath + "application.yml";
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str + "\n");
        }
        br.close();
        fr.close();
        return Rest.ok(sb.toString());
    }
}
