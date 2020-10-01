package com.apocalypse.common.data.redis.redisson.codec;

import com.apocalypse.common.data.mybatis.util.json.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.redisson.codec.JsonJacksonCodec;

/**
 * @author <a href="kaihuijing@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/2
 */
public class CustomJsonJacksonCodec extends JsonJacksonCodec {

    public CustomJsonJacksonCodec() {
        super(JsonUtil.defaultObjectMapper().copy());
    }

    public CustomJsonJacksonCodec(ClassLoader classLoader) {
        super(createObjectMapper(classLoader, JsonUtil.defaultObjectMapper().copy()));
    }

    public CustomJsonJacksonCodec(ClassLoader classLoader, CustomJsonJacksonCodec codec) {
        super(createObjectMapper(classLoader, codec.mapObjectMapper.copy()));
    }

    @Override
    protected void init(ObjectMapper objectMapper) {
        super.init(objectMapper);

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new SimpleModule());
    }
}
