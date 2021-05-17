package com.apocalypse.example.controller.io;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.core.api.BaseResponse;
import com.apocalypse.common.core.api.Rest;
import com.apocalypse.common.util.json.JsonUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/8
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/server")
@Api(value = "服务器", tags = {"服务器"}, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ServerController {

    @GetMapping("/bio/start")
    @ApiOperation(value = "启动服务(BIO)", notes = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public Rest<BaseResponse> startBIOServer() throws InterruptedException {
        ThreadUtil.execute(BIOServer::start);
        TimeUnit.SECONDS.sleep(1);
        ServerSocket serverSocket = BIOServer.serverSocket;
        String host = serverSocket.getInetAddress().getHostAddress();
        int port = serverSocket.getLocalPort();

        ObjectNode objectNode = JsonUtil.emptyObjectNode();
        objectNode.put("host", host);
        objectNode.put("port", port);


        return Rest.vector("server", objectNode, ObjectNode.class);
    }

    @GetMapping("/bio/stop")
    @ApiOperation(value = "停止服务(BIO)", notes = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public Rest<BaseResponse> stopBIOServer() throws IOException {
        BIOServer.stop();
        return Rest.success();
    }

    @GetMapping("/bio/messages(BIO)")
    @ApiOperation(value = "接收到消息(BIO)", notes = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public Rest<BaseResponse> messagesInBIOServer() {
        return Rest.vector("messages", BIOServer.messages, ConcurrentLinkedQueue.class);
    }

    @GetMapping("/nio/start")
    @ApiOperation(value = "启动服务(NIO)", notes = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public Rest<BaseResponse> startNIOServer() throws InterruptedException {
        ThreadUtil.execute(NIOServer::selector);
        TimeUnit.SECONDS.sleep(1);
        InetSocketAddress localSocketAddress = (InetSocketAddress) NIOServer.ssc.socket().getLocalSocketAddress();

        ObjectNode objectNode = JsonUtil.emptyObjectNode();
        objectNode.put("host", localSocketAddress.getAddress().getHostAddress());
        objectNode.put("port", localSocketAddress.getPort());
        return Rest.vector("server", objectNode, ObjectNode.class);
    }

    @GetMapping("/nio/stop")
    @ApiOperation(value = "停止服务(NIO)", notes = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public Rest<BaseResponse> stopNIOServer() throws IOException {
        NIOServer.stop();
        return Rest.success();
    }

    @GetMapping("/nio/messages(NIO)")
    @ApiOperation(value = "接收到消息(NIO)", notes = "", produces = MediaType.TEXT_PLAIN_VALUE)
    public Rest<BaseResponse> messagesInNIOServer() {
        return Rest.vector("messages", BIOServer.messages, ConcurrentLinkedQueue.class);
    }

}

class NIOServer {
    static Selector selector;

    static ServerSocketChannel ssc;

    static ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<>();

    private static final String HOST = "127.0.0.1";

    private static final int BUF_SIZE = 1024;

    private static final int PORT = 1314;

    private static final int TIMEOUT = 3000;

    public static void selector() {
        try {
            selector = Selector.open();

            ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);

            ServerSocket serverSocket = ssc.socket();
            serverSocket.bind(new InetSocketAddress(HOST, PORT));

            ssc.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                while (selector.isOpen() && selector.select(TIMEOUT) > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        if(key.isAcceptable()){
                            handleAccept(key);
                        }
                        if(key.isReadable()){
                            handleRead(key);
                        }
                        if(key.isWritable() && key.isValid()){
                            handleWrite(key);
                        }
                        if(key.isConnectable()){
                            System.out.println("isConnectable = true");
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        try {
            selector.close();
            ssc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleAccept(SelectionKey key) throws IOException{
        ServerSocketChannel ssChannel = (ServerSocketChannel)key.channel();
        SocketChannel sc = ssChannel.accept();
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
    }

    public static void handleRead(SelectionKey key) throws IOException{
        CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();
        SocketChannel sc = (SocketChannel)key.channel();
        ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        int byteRead = sc.read(byteBuffer);
        StringBuilder sb = new StringBuilder();
        while(byteRead > 0){
            byteBuffer.flip();
            charsetDecoder.decode(byteBuffer, charBuffer, false);
            charBuffer.flip();
            sb.append(new String(charBuffer.array(), 0, charBuffer.limit()));

            charBuffer.clear();
            byteBuffer.compact();
            byteRead = sc.read(byteBuffer);
        }
        if (StrUtil.isNotEmpty(sb)) {
            InetSocketAddress remoteAddress = (InetSocketAddress) sc.getRemoteAddress();
            String formatMsg = StrUtil.format("producer ip: {}, port: {}, time: {}, msg: {}", remoteAddress.getAddress(),
                    remoteAddress.getPort(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)), sb);
            System.out.println("read:" + sb);
            messages.add(formatMsg);
        }
    }

    public static void handleWrite(SelectionKey key) throws IOException{
        CharsetDecoder charsetDecoder = StandardCharsets.UTF_8.newDecoder();
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        int byteRead = sc.read(byteBuffer);
        StringBuilder sb = new StringBuilder();
        while (byteRead > 0) {
            byteBuffer.flip();
            charsetDecoder.decode(byteBuffer, charBuffer, false);
            charBuffer.flip();
            sb.append(new String(charBuffer.array(), 0, charBuffer.limit()));

            charBuffer.clear();
            byteBuffer.compact();
            byteRead = sc.read(byteBuffer);
        }
        if (StrUtil.isNotEmpty(sb)) {
            InetSocketAddress remoteAddress = (InetSocketAddress) sc.getRemoteAddress();
            String formatMsg = StrUtil.format("producer ip: {}, port: {}, time: {}, msg: {}", remoteAddress.getAddress(),
                    remoteAddress.getPort(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)), sb);
            System.out.println("write:" + formatMsg);
        }
    }
}

class BIOServer {
    static ServerSocket serverSocket;

    static ConcurrentLinkedQueue<String> messages = new ConcurrentLinkedQueue<>();

    private static final int PORT = 1314;

    public static void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                InetSocketAddress isa = ((InetSocketAddress) socket.getRemoteSocketAddress());
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = reader.readLine();
                String formatMsg = StrUtil.format("producer ip: {}, port: {}, time: {}, msg: {}", isa.getAddress(),
                        isa.getPort(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)), msg);
                messages.add(formatMsg);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
