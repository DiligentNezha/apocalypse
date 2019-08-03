package com.apocalypse.example.config;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new Formatter<LocalDate>() {
            @Override
            public String print(LocalDate date, Locale locale) {
                return date.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN, locale));
            }

            @Override
            public LocalDate parse(String date, Locale locale) throws ParseException {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN, locale));
            }
        });
        registry.addFormatter(new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String dateTime, Locale locale) throws ParseException {
                return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN,
                        locale));
            }

            @Override
            public String print(LocalDateTime dateTime, Locale locale) {
                return dateTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN, locale));
            }
        });
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2.序列化配置
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(Long.class, new ToStringSerializer());
        serializeConfig.put(LocalDate.class, (serializer, object, fieldName, fieldType, features) -> {
            SerializeWriter out = serializer.out;

            if (object == null) {
                out.writeNull();
            } else {
                if (fieldType == null) {
                    fieldType = object.getClass();
                }
                if (LocalDate.class == fieldType) {
                    LocalDate date = (LocalDate) object;
                    out.writeString(date.format(DateTimeFormatter.ofPattern(DatePattern.CHINESE_DATE_PATTERN)));
                }
            }
        });
        serializeConfig.put(LocalDateTime.class, (serializer, object, fieldName, fieldType, features) -> {
            SerializeWriter out = serializer.out;
            if (object == null) {
                out.writeNull();
            } else {
                if (fieldType == null) {
                    fieldType = object.getClass();
                }
                if (LocalDateTime.class == fieldType) {
                    LocalDateTime dateTime = (LocalDateTime) object;
//                    out.writeString(dateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd天 HH时mm分ss秒")));
                    out.writeString(dateTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
                }
            }
        });
        //3.添加fastJson的配置信息;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializeConfig(serializeConfig);
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //将FastJsonHttpMessageConverter放在MappingJackson2HttpMessageConverter前边
        converters.add(converters.size() - 1, fastJsonHttpMessageConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //把swagger文档地址交由资源处理器处理
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
