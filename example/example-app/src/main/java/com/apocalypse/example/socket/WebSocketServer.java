package com.apocalypse.example.socket;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/8
 */
@Slf4j
@Component
@ServerEndpoint(value = "/socket",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class})
public class WebSocketServer {

    @OnOpen
    public void onOpen(Session session) {
        log.info("新的连接打开[{}]", session.getId());
    }

    @OnMessage
    public void onMessage(Session session, Message msg) {
        log.info("接收到[{}]消息[{}]", session.getId(), msg);
        ChatInfoHolder.receiveMsg(msg);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[{}]发生错误[{}]", session.getId(), error.getMessage(), error);
    }

    @OnClose
    public void onClose(Session session) {
        log.error("[{}]断开连接", session.getId());
    }

}

@Getter
class ChatInfoHolder {
    private static final AtomicInteger messageCount = new AtomicInteger();

    private static final ConcurrentLinkedQueue<Message> messages = new ConcurrentLinkedQueue<>();

    public static void receiveMsg(Message message) {
        messageCount.incrementAndGet();
        messages.add(message);
    }
}

@Getter
@Setter
@Accessors(chain = true)
class Message {
    /**
     * 发送人
     */
    private String sender;

    /**
     * 消息
     */
    private String msg;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
}
