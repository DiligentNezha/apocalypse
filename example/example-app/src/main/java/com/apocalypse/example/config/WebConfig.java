package com.apocalypse.example.config;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

//    @Override
//    protected void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedHeaders("*")
//                .allowedMethods("*")
//                .allowedOrigins("*");
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedOriginPattern("*");
        //header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedHeader("*");
        //允许的请求方法，PSOT、GET等
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setExposedHeaders(Arrays.asList("X-Auth-Token"));
        corsConfiguration.setAllowCredentials(true);
        //配置允许跨域访问的url
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new Formatter<LocalDate>() {
            @Override
            public String print(LocalDate localDate, Locale locale) {
                return localDate.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN, locale));
            }

            @Override
            public LocalDate parse(String localDate, Locale locale) throws ParseException {
                return LocalDate.parse(localDate, DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN, locale));
            }
        });
        registry.addFormatter(new Formatter<LocalTime>() {
            @Override
            public LocalTime parse(String localTime, Locale locale) throws ParseException {
                return LocalTime.parse(localTime, DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN,
                        locale));
            }

            @Override
            public String print(LocalTime localTime, Locale locale) {
                return localTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN, locale));
            }
        });
        registry.addFormatter(new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String localDateTime, Locale locale) throws ParseException {
                return LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN,
                        locale));
            }

            @Override
            public String print(LocalDateTime localDateTime, Locale locale) {
                return localDateTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN, locale));
            }
        });
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    @Primary
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .featuresToEnable(JsonParser.Feature.ALLOW_COMMENTS)
                .featuresToDisable()
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)))

                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)))

                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));

    }

//    @Override
//    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        //1.需要定义一个convert转换消息的对象;
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        //2.序列化配置
//        SerializeConfig serializeConfig = new SerializeConfig();
//        serializeConfig.put(Long.class, new ToStringSerializer());
//        serializeConfig.put(LocalDate.class, (serializer, object, fieldName, fieldType, features) -> {
//            SerializeWriter out = serializer.out;
//
//            if (object == null) {
//                out.writeNull();
//            } else {
//                if (fieldType == null) {
//                    fieldType = object.getClass();
//                }
//                if (LocalDate.class == fieldType) {
//                    LocalDate date = (LocalDate) object;
//                    out.writeString(date.format(DateTimeFormatter.ofPattern(DatePattern.CHINESE_DATE_PATTERN)));
//                }
//            }
//        });
//        serializeConfig.put(LocalDateTime.class, (serializer, object, fieldName, fieldType, features) -> {
//            SerializeWriter out = serializer.out;
//            if (object == null) {
//                out.writeNull();
//            } else {
//                if (fieldType == null) {
//                    fieldType = object.getClass();
//                }
//                if (LocalDateTime.class == fieldType) {
//                    LocalDateTime dateTime = (LocalDateTime) object;
////                    out.writeString(dateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd天 HH时mm分ss秒")));
//                    out.writeString(dateTime.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
//                }
//            }
//        });
//        //3.添加fastJson的配置信息;
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializeConfig(serializeConfig);
//        //3处理中文乱码问题
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        //4.在convert中添加配置信息.
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
//        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//        //将FastJsonHttpMessageConverter放在MappingJackson2HttpMessageConverter前边
//        converters.add(converters.size() - 1, fastJsonHttpMessageConverter);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //把swagger文档地址交由资源处理器处理
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
