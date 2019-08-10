package com.apocalypse.example.controller.io;

import com.apocalypse.common.util.HttpContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/5
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/bio")
@Api(value = "BIO案例", tags = {"BIO案例"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class BioController {

    private static final String FILE_PATH =
            BioController.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1) + "application.yml";

    @GetMapping("/reader/fileReader")
    @ApiOperation(value = "文件读取(FileReader)", notes = "读取application.yml", produces = MediaType.TEXT_PLAIN_VALUE)
    public void fileReader() throws Exception {
        FileReader fr = new FileReader(FILE_PATH);
        StringBuilder sb = new StringBuilder();

        char[] temp = new char[1024];
        int len;
        while ((len = fr.read(temp)) != -1) {
            sb.append(Arrays.copyOf(temp, len));
        }
        fr.close();

        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = response.getWriter();
        writer.println(sb);
        writer.flush();
        writer.close();
    }

    @GetMapping("/reader/bufferedReader")
    @ApiOperation(value = "文件读取(BufferedReader)", notes = "读取application.yml", produces = MediaType.TEXT_PLAIN_VALUE)
    public void bufferedReader() throws Exception {
        FileReader fr = new FileReader(FILE_PATH);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str + "\n");
        }
        br.close();
        fr.close();

        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = response.getWriter();
        writer.println(sb);
        writer.flush();
        writer.close();
    }

    @GetMapping("/reader/lineNumberReader")
    @ApiOperation(value = "文件读取(LineNumberReader)", notes = "读取application.yml", produces = MediaType.TEXT_PLAIN_VALUE)
    public void lineNumberReader() throws Exception {
        FileReader fr = new FileReader(FILE_PATH);
        LineNumberReader lnr = new LineNumberReader(fr);

        StringBuilder sb = new StringBuilder();
        lnr.lines().forEachOrdered(line -> {
            sb.append(lnr.getLineNumber() + ":" + line + "\n");
        });

        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = response.getWriter();
        writer.println(sb);
        writer.flush();
        writer.close();
    }

}
