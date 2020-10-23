package com.apocalypse.common.data.redis.redisson.codec;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.security.jackson2.SecurityJackson2Modules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/6/2
 */
public class CustomJsonJacksonCodec extends JsonJacksonCodec {

//    @Override
//    protected void init(ObjectMapper objectMapper) {
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
//
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
//        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
//
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
//
//        objectMapper.registerModule(javaTimeModule);
//        objectMapper.registerModule(new SimpleModule());
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        objectMapper.registerModule(new CoreJackson2Module());
//        objectMapper.registerModule(new WebJackson2Module());
//        objectMapper.registerModule(new WebServletJackson2Module());
//        objectMapper.registerModule(new WebServerJackson2Module());
////        objectMapper.registerModule(new CasJackson2Module());
////        objectMapper.registerModule(new OAuth2ClientJackson2Module());
//
//    }
//
//    public CustomJsonJacksonCodec() {
//        ObjectMapper objectMapper = getObjectMapper();
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
//
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
//        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
//
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
//
//        objectMapper.registerModule(javaTimeModule);
//        objectMapper.registerModule(new SimpleModule());
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        objectMapper.registerModule(new CoreJackson2Module());
//        objectMapper.registerModule(new WebJackson2Module());
//        objectMapper.registerModule(new WebServletJackson2Module());
//        objectMapper.registerModule(new WebServerJackson2Module());
//
//        System.out.println("init:" + this);
//    }
//
//    public CustomJsonJacksonCodec(ObjectMapper oldObjectMapper) {
//        ObjectMapper objectMapper = oldObjectMapper.copy();
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        objectMapper.registerModule(new CoreJackson2Module());
////        objectMapper.registerModule(new CasJackson2Module());
//        objectMapper.registerModule(new WebJackson2Module());
//        objectMapper.registerModule(new WebServletJackson2Module());
//        objectMapper.registerModule(new WebServerJackson2Module());
////        objectMapper.registerModule(new OAuth2ClientJackson2Module());
//
//        init(objectMapper);
//        initTypeInclusion(objectMapper);
//    }
//
//    public CustomJsonJacksonCodec(ClassLoader classLoader) {
//        ObjectMapper objectMapper = JsonUtil.defaultObjectMapper().copy();
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        objectMapper.registerModule(new CoreJackson2Module());
////        objectMapper.registerModule(new CasJackson2Module());
//        objectMapper.registerModule(new WebJackson2Module());
//        objectMapper.registerModule(new WebServletJackson2Module());
//        objectMapper.registerModule(new WebServerJackson2Module());
//        init(objectMapper);
//        initTypeInclusion(objectMapper);
//    }
//

//    public CustomJsonJacksonCodec(ClassLoader classLoader, CustomJsonJacksonCodec codec) {
//        TypeFactory tf = TypeFactory.defaultInstance().withClassLoader(classLoader);
////        codec.getObjectMapper().copy().setTypeFactory(tf);
//        codec.getObjectMapper().setTypeFactory(tf);
//
//    }
//
//
//    @Override
//    public String toString() {
//        return getClass().getName() + "@" + Integer.toHexString(hashCode()) + ":objectMapper->" + getObjectMapper();
//    }

    public CustomJsonJacksonCodec() {
//        super(createObjectMapperWithSecurity());
        super();
        customObjectMapper();
    }

    public CustomJsonJacksonCodec(ClassLoader classLoader) {
//        super(createObjectMapper(classLoader, createObjectMapperWithSecurity()));
        super(classLoader);
        customObjectMapper();
    }

    public CustomJsonJacksonCodec(ClassLoader classLoader, CustomJsonJacksonCodec codec) {
//        super(createObjectMapper(classLoader, codec.mapObjectMapper.copy()));
        super(classLoader, codec);
        customObjectMapper();
    }

//    @Override
//    protected void init(ObjectMapper objectMapper) {
//        super.init(objectMapper);
//
//        objectMapper.disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS);
//        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
//
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
//        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
//
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
//
//        objectMapper.registerModule(javaTimeModule);
//        objectMapper.registerModule(new SimpleModule());
//        objectMapper.registerModules(SecurityJackson2Modules.getModules(getClassLoader()));
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        objectMapper.registerModule(new CoreJackson2Module());
//        objectMapper.registerModule(new WebJackson2Module());
//        objectMapper.registerModule(new WebServletJackson2Module());
//        objectMapper.registerModule(new WebServerJackson2Module());
//        objectMapper.registerModule(new CasJackson2Module());
//        objectMapper.registerModule(new OAuth2ClientJackson2Module());

//    }

    private ObjectMapper customObjectMapper() {
//        ObjectMapper objectMapper = JsonUtil.defaultObjectMapper().copy();
        ObjectMapper objectMapper = getObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));

        objectMapper.registerModule(javaTimeModule);
        objectMapper.registerModule(new SimpleModule());
//        objectMapper.addMixIn(SecurityContextImpl.class, SecurityContextImplMixin.class);
        objectMapper.registerModules(SecurityJackson2Modules.getModules(getClassLoader()));
        return objectMapper;
    }
}
