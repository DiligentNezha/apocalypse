package com.apocalypse.example.controller.io;

import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.dto.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
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
@RestController
@RequestMapping("/nio")
@Api(value = "NIO案例", tags = {"NIO案例"}, consumes = "application/json")
public class NioController {

    @PostMapping("/file/read")
    @ApiOperation(value = "文件读取(Files类静态方法)", notes = "使用Files类的read方法读取application", produces = "application/json")
    public Rest<String> fileRead() throws Exception {
        String classDirPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
        String filePath = classDirPath + "application.yml";
        String data = StrUtil.str(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        return Rest.ok(data);
    }

    @PostMapping("/buffer/read")
    @ApiOperation(value = "文件读取(Buffer方式)", notes = "使用Buffer读取application", produces = "application/json")
    public Rest<String> buffRead() throws Exception {
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
        return Rest.ok(sb.toString());
    }

}
