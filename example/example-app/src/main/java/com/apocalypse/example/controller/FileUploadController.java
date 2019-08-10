package com.apocalypse.example.controller;

import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.util.HttpContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/download/servletOutputStream")
    @ApiOperation(value = "文件下载(ServletOutputStream 方式)", notes = "文件下载", produces =
            MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadUseServletOutputStream() throws IOException {
        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
//        response.setContentType("application/vnd.ms-excel");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-disposition", StrUtil.format("attachment;filename={}.yml",
                StrUtil.str("配置文件".getBytes("gbk"), StandardCharsets.ISO_8859_1)));

        String filePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1) + "application.yml";

        Files.copy(Paths.get(filePath), response.getOutputStream());
    }

    @GetMapping("/download/responseEntity")
    @ApiOperation(value = "文件下载(ResponseEntity 方式)", notes = "文件下载", produces =
            MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> downloadUseResponseEntity() throws IOException {
        ClassPathResource fileResource = new ClassPathResource("application.yml");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", StrUtil.format("attachment;filename={}",
                StrUtil.str(fileResource.getFilename().getBytes("gbk"), StandardCharsets.ISO_8859_1)));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(fileResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(fileResource.getInputStream()));
    }
}
