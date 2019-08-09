package com.apocalypse.example.controller.io;
import	java.nio.ByteBuffer;
import	java.net.InetSocketAddress;

import com.apocalypse.common.dto.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/8
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/client")
@Api(value = "客户端", tags = {"客户端"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    public static final String HOST = "127.0.0.1";

    public static final int PORT = 1314;

    @GetMapping("/bio/message/send")
    @ApiOperation(value = "发送消息(发送到BIO服务器)", notes = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public Rest<Boolean> sendMsgToBIOServer(@RequestParam @NotBlank String msg) {
        Socket socket = null;
        try {
            socket = new Socket(HOST, PORT);
            BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            buf.write(msg);
            buf.flush();
            buf.close();
            socket.close();
        } catch (IOException e) {

        }
        return Rest.ok(Boolean.TRUE);
    }

    @GetMapping("/nio/message/send")
    @ApiOperation(value = "发送消息(发送到NIO服务器)", notes = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public Rest<Boolean> sendMsgToNIOServer(@RequestParam @NotBlank String msg) throws IOException, InterruptedException {
        CharsetEncoder charsetEncoder = StandardCharsets.UTF_8.newEncoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);

        charBuffer.clear();
        charBuffer.put(msg);

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(HOST, PORT));

        if (socketChannel.finishConnect()) {
            charBuffer.flip();
            ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer);

            socketChannel.write(byteBuffer);
            socketChannel.close();
        }

        return Rest.ok(Boolean.TRUE);
    }
}
