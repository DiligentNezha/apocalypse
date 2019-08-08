package com.apocalypse.example.controller;

import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.util.HttpContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/5
 */
@Slf4j
@RestController
@RequestMapping("/file")
@Api(value = "文件上传下载", tags = {"文件上传下载"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class FileUploadController {

    @PostMapping("/upload/single")
    @ApiOperation(value = "单个文件上传", notes = "单个文件上传", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        Path path = Paths.get("/" + originalFilename);
        file.transferTo(path);
        return Rest.ok(path.toString());
    }

    @ApiImplicitParam(name = "file[]", value = "文件流对象,接收数组格式", required = true, dataType = "__File", allowMultiple = true)
    @PostMapping(value = "/upload/multi")
    @ApiOperation(value = "多文件上传", notes = "多文件上传", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rest<List<String>> multiUpload(@RequestParam(value="file[]") MultipartFile[] files) throws IOException {
        Arrays.stream(files).forEach(file -> {
            String originalFilename = file.getOriginalFilename();
            Path path = Paths.get("/" + originalFilename);
            try {
                file.transferTo(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return Rest.ok(Arrays.stream(files).map(file -> file.getOriginalFilename()).collect(Collectors.toList()));
    }

    @GetMapping("/download")
    @ApiOperation(value = "文件下载", notes = "文件下载", produces = "application/octet-stream")
    public void download() throws IOException {
        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
//        response.setContentType("application/vnd.ms-excel");
        response.setContentType("text/plain");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-disposition", StrUtil.format("attachment;filename={}.yml",
                StrUtil.str("配置文件".getBytes("gbk"), StandardCharsets.ISO_8859_1)));
        ServletOutputStream os = response.getOutputStream();
        String classDirPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        String filePath = classDirPath + "application.yml";
        byte[] b = Files.readAllBytes(Paths.get(filePath));
        os.write(b);
        os.flush();
        os.close();
    }
}
