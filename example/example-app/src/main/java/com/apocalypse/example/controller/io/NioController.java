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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
import java.sql.ResultSet;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/5
 */
@Slf4j
@Validated
@Controller
@RequestMapping("/nio")
@Api(value = "NIO案例", tags = {"NIO案例"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class NioController {

    private static final String FILE_PATH =
            BioController.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1) + "application.yml";

    @ResponseBody
    @GetMapping("/charBuffer/manipulate")
    @ApiOperation(value = "CharBuffer操作", notes = "CharBuffer 操作案例", produces = MediaType.TEXT_PLAIN_VALUE)
    public Rest<String> charBuffer() {
        // mark <= position <= limit <= capacity
        //capacity 不可修改
        CharBuffer charBuffer = CharBuffer.allocate(1024);

        //新申请的CharBuffer mark = -1, position = 0, limit = 1024

        charBuffer.put("机智的");
        //写入三个字符，数组内容为 机智的 mark = -1, position = 3, limit = 1024

        charBuffer.put("小哪吒");
        //继续写入三个字符，数组内容为 机智的小哪吒 mark = -1， position = 6, limit = 1024
        charBuffer.flip();
        //mark = -1, limit = position = 6, position = 0

        char[] pre = new char[3];
        charBuffer.get(pre);
        //读取三个字符 mark = -1, position = 3, limit = 6

        charBuffer.put("是");
        //写入一个字符，position = 3 处的字符 小 会被 是 覆盖，数组内容为 机智的是哪吒 mark = -1, position = 4, limit = 6

        charBuffer.compact();
        //此时数组剩余未读取的字符即 position = 4 到 limit = 6 之间的字符 哪吒 被拷贝到Buffer起始处覆盖 机智，此时测试数组内容为
        // 哪吒的是哪吒，重置
        //mark = -1, position = limit - position (6 - 4) = 2, limit = 1024
        //对旧的 哪吒 两个字符进行标记
        charBuffer.mark();
        //mark = 2, position = 2, limit = 1024

        charBuffer.put("我是新写入的内容");
        //写入新内容， 从 position = 2 处进行写入， 测试数组内容为 哪吒我是新写入的内容, position = position + 8（新写入内容的长度） = 10, limit = 1024

        charBuffer.reset();
        //将 position 重置回刚做的标记，此时 mark = 2, position = 2, limit = 1024

        charBuffer.flip();
        //mark = -1, position = 0, limit = position = 2

        charBuffer.get(pre, 0, charBuffer.limit());
        //此时读取到 position = 2 之前的数据到 pre 中， pre 之前读到的 机智的 前两个会被 刚读到的 哪吒 覆盖 成为 哪吒的
        //-1, position = 2, limit = 2
        charBuffer.compact();
        //mark = -1, position = limit - position (2 - 2) = 0, limit = 1024
        //最终 charBuffer 中数组的内容为 哪吒我是新写入的内容
        return Rest.ok(StrUtil.str(charBuffer.array(), StandardCharsets.UTF_8));
    }

    @GetMapping("/file/read")
    @ApiOperation(value = "文件读取(Files类静态方法)", notes = "使用Files类的read方法读取application", produces = MediaType.TEXT_PLAIN_VALUE)
    public void fileRead() throws Exception {
        String data = StrUtil.str(Files.readAllBytes(Paths.get(FILE_PATH)), StandardCharsets.UTF_8);
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
        CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();

        FileChannel channel = new FileInputStream(FILE_PATH).getChannel();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);
        CharBuffer charBuf = CharBuffer.allocate(1024);

        int byteRead = channel.read(byteBuf);
        StringBuilder sb = new StringBuilder();

        while (byteRead > 0) {
            byteBuf.flip();
            charsetDecoder.decode(byteBuf, charBuf, false);
            charBuf.flip();

            sb.append(new String(charBuf.array(), 0, charBuf.limit()));

            charBuf.clear();
            //由于编码原因，byteBuf中数据会有剩余，将剩余的字节拷贝到byteBuf的起始处，然后将position设置为最后一个未读元素后边
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
