package com.apocalypse.example.socket;

import com.alibaba.fastjson.JSON;

import javax.websocket.*;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/8/8
 */
public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(Message object) throws EncodeException {
        return JSON.toJSONString(object);
    }
}
