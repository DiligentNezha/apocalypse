package com.apocalypse.example.controller.io;

import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.dto.Rest;
import com.apocalypse.common.util.HttpContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
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
@Controller
@RequestMapping("/nio")
@Api(value = "NIO案例", tags = {"NIO案例"}, consumes = "application/json")
public class NioController {

    @GetMapping("/file/read")
    @ApiOperation(value = "文件读取(Files类静态方法)", notes = "使用Files类的read方法读取application", produces = MediaType.TEXT_PLAIN_VALUE)
    public void fileRead() throws Exception {
        String classDirPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        String filePath = classDirPath + "application.yml";
        String data = StrUtil.str(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = response.getWriter();
        writer.println(data);
        writer.flush();
        writer.close();
    }

    @GetMapping("/buffer/read")
    @ApiOperation(value = "文件读取(Buffer方式)", notes = "使用Buffer读取application", produces = MediaType.TEXT_PLAIN_VALUE)
    public void buffRead() throws Exception {
        String classDirPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        String filePath = classDirPath + "application.yml";
        CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();

        FileChannel channel = new FileInputStream(filePath).getChannel();
        ByteBuffer byteBuf = ByteBuffer.allocate(1024);
        CharBuffer charBuf = CharBuffer.allocate(1024);

        int byteRead = channel.read(byteBuf);
        StringBuilder sb = new StringBuilder();

        while (byteRead != -1) {
            byteBuf.flip();
            charsetDecoder.decode(byteBuf, charBuf, false);
            charBuf.flip();

            sb.append(new String(charBuf.array(), 0, charBuf.limit()));

            charBuf.clear();
            byteBuf.compact();
            byteRead = channel.read(byteBuf);
        }
        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = response.getWriter();
        writer.println(sb.toString());
        writer.flush();
        writer.close();
    }

}
