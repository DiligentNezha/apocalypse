package com.apocalypse.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.dto.Rest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.time.temporal.ChronoField.values;

@Slf4j
@RestController
@RequestMapping("/example")
@Api(value = "Java8Date", tags = {"Java8Date"}, consumes = "application/json")
public class Java8DateController {

    @GetMapping("/localDate")
    @ApiOperation(value = "LocalDate", notes = "LocalDate测试", produces = "application/json")
    public Rest<Map<String, Object>> localDate() {
        LocalDate date = LocalDate.now();
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("当前天", date);
        results.put("去年今天", date.plusYears(-1));
        results.put("明年今天", date.plusYears(1));
        results.put("上个月今天", date.plusMonths(-1));
        results.put("下个月今天", date.plusMonths(1));
        results.put("上周", date.plusWeeks(-1));
        results.put("下周", date.plusWeeks(1));
        results.put("昨天", date.plusDays(-1));
        results.put("明天", date.plusDays(1));
        results.put("当前天的开始", date.atStartOfDay());
        results.put("当前天的中午", date.atTime(LocalTime.NOON));
        results.put("当前天的结束", date.atTime(LocalTime.MAX));
        results.put("当前年", date.getYear());
        results.put("当前月", date.getMonth());
        results.put("当前月份值", date.getMonthValue());
        results.put("当前天是本月的第几天", date.getDayOfMonth());
        results.put("当前天是今年的第几天", date.getDayOfYear());
        results.put("今天是本周的第几天（默认第一天是星期一）", date.getDayOfWeek());
        results.put("当前年天数", date.lengthOfYear());
        results.put("当前月天数", date.lengthOfMonth());
        results.put("1970-01-01 距离几天是多少天", ChronoUnit.DAYS.between(LocalDate.of(1970, 1, 1), date));
        Arrays.stream(values()).filter(date::isSupported)
                .forEach(chronoField -> {
                    results.put(chronoField.name(), date.getLong(chronoField));
                    results.put(chronoField.name() + ".range", date.range(chronoField));
                });
        return Rest.ok(results);
    }

    @GetMapping("/localTime")
    @ApiOperation(value = "LocalTime", notes = "LocalTime测试", produces = "application/json")
    public Rest<Map<String, Object>> register() {
        LocalTime time = LocalTime.now();
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("当前时间", time);
        results.put("上个小时", time.plusHours(-1));
        results.put("下个小时", time.plusHours(1));
        results.put("上一分钟", time.plusMinutes(-1));
        results.put("下一分钟", time.plusMinutes(1));
        results.put("上一秒", time.plusSeconds(-1));
        results.put("下一秒", time.plusSeconds(1));
        results.put("当前小时", time.getHour());
        results.put("当前分钟", time.getMinute());
        results.put("当前秒数", time.getSecond());
        results.put("当前纳秒数", time.getNano());
        Arrays.stream(values()).filter(time::isSupported)
                .forEach(chronoField -> {
                    results.put(chronoField.name(), time.getLong(chronoField));
                    results.put(chronoField.name() + ".range", time.range(chronoField));
                });
        return Rest.ok(results);
    }

    @GetMapping("/localDateTime")
    @ApiOperation(value = "LocalDateTime", notes = "LocalDateTime测试", produces = "application/json")
    public Rest<Map<String, Object>> localDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("当前时间", dateTime);
        results.put("去年今天", dateTime.plusYears(-1));
        results.put("明年今天", dateTime.plusYears(1));
        results.put("上个月今天", dateTime.plusMonths(-1));
        results.put("下个月今天", dateTime.plusMonths(1));
        results.put("上周", dateTime.plusWeeks(-1));
        results.put("下周", dateTime.plusWeeks(1));
        results.put("昨天", dateTime.plusDays(-1));
        results.put("明天", dateTime.plusDays(1));
        results.put("当前天的开始", dateTime.toLocalDate().atStartOfDay());
        results.put("当前天的中午", dateTime.toLocalDate().atTime(LocalTime.NOON));
        results.put("当前天的结束", dateTime.toLocalDate().atTime(LocalTime.MAX));
        results.put("当前年", dateTime.getYear());
        results.put("当前月", dateTime.getMonth());
        results.put("当前月份值", dateTime.getMonthValue());
        results.put("当前天是本月的第几天", dateTime.getDayOfMonth());
        results.put("当前天是今年的第几天", dateTime.getDayOfYear());
        results.put("今天是本周的第几天（默认第一天是星期一）", dateTime.getDayOfWeek());
        results.put("当前年天数", dateTime.toLocalDate().lengthOfYear());
        results.put("当前月天数", dateTime.toLocalDate().lengthOfMonth());
        results.put("1970-01-01 距离几天是多少天", ChronoUnit.DAYS.between(LocalDate.of(1970, 1, 1), dateTime));

        results.put("上个小时", dateTime.plusHours(-1));
        results.put("下个小时", dateTime.plusHours(1));
        results.put("上一分钟", dateTime.plusMinutes(-1));
        results.put("下一分钟", dateTime.plusMinutes(1));
        results.put("上一秒", dateTime.plusSeconds(-1));
        results.put("下一秒", dateTime.plusSeconds(1));
        results.put("当前小时", dateTime.getHour());
        results.put("当前分钟", dateTime.getMinute());
        results.put("当前秒数", dateTime.getSecond());
        results.put("当前纳秒数", dateTime.getNano());
        Arrays.stream(values()).filter(dateTime::isSupported)
                .forEach(chronoField -> {
                    results.put(chronoField.name(), dateTime.getLong(chronoField));
                    results.put(chronoField.name() + ".range", dateTime.range(chronoField));
                });
        return Rest.ok(results);
    }

    @GetMapping("/zonedDateTime")
    @ApiOperation(value = "ZonedDateTime", notes = "ZonedDateTime测试", produces = "application/json")
    public Rest<Map<String, Object>> zonedDateTime() {
        ZoneId systemDefaultZonId = ZoneId.systemDefault();

        ZonedDateTime now = ZonedDateTime.now(systemDefaultZonId);
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("系统默认时区", systemDefaultZonId.getId());
        results.put("当前时间", now.toLocalDateTime());
        ZoneId.getAvailableZoneIds().stream().forEach(zoneId -> {
            results.put(zoneId, now.withZoneSameInstant(ZoneId.of(zoneId)));
        });
        return Rest.ok(results);
    }

    @GetMapping("/period")
    @ApiOperation(value = "Period", notes = "Period测试", produces = "application/json")
    public Rest<Map<String, Object>> period() {
        LocalDate beginDate = LocalDate.of(1994, 4, 4);
        LocalDate endDate = LocalDate.of(2019, 8, 3);
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("开始日期", beginDate);
        results.put("结束日期", endDate);
        Period period = Period.between(beginDate, endDate);
        results.put("period", period);
        results.put("开始日期和结束日期之间相隔年", period.getYears());
        results.put("开始日期和结束日期之间相隔月", period.getMonths());
        results.put("开始日期和结束日期之间相隔天", period.getDays());
        return Rest.ok(results);
    }

    @GetMapping("/zoneId")
    @ApiOperation(value = "ZoneId", notes = "ZoneId测试", produces = "application/json")
    public Rest<Map<String, Object>> zoneId() {
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("默认时区", ZoneId.systemDefault());
        ZoneId.getAvailableZoneIds().stream().sorted().forEach(zoneId -> results.put(zoneId, new JSONObject()
                .fluentPut("zoneId", zoneId)
                .fluentPut("当前时间", ZonedDateTime.now(ZoneId.of(zoneId)))));
        return Rest.ok(results);
    }

    @GetMapping("/instance")
    @ApiOperation(value = "Instance", notes = "Instance测试", produces = "application/json")
    public Rest<Map<String, Object>> instance() {
        Instant now = Instant.now();
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("当前秒数", now.getEpochSecond());
        results.put("当前毫妙数", now.getLong(ChronoField.MILLI_OF_SECOND));
        results.put("当前微秒数", now.getLong(ChronoField.MICRO_OF_SECOND));
        results.put("当前纳秒数", now.getNano());
        Arrays.stream(values()).filter(now::isSupported)
                .forEach(chronoField -> {
                    results.put(chronoField.name(), now.getLong(chronoField));
                    results.put(chronoField.name() + ".range", now.range(chronoField));
                });
        return Rest.ok(results);
    }

    @GetMapping("/duration")
    @ApiOperation(value = "Duration", notes = "Duration测试", produces = "application/json")
    public Rest<Map<String, Object>> duration() {
        LocalDateTime beginDateTime = LocalDateTime.of(LocalDate.of(1994, 4, 4), LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.of(2019, 8, 3), LocalTime.MAX);
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("开始日期", beginDateTime);
        results.put("结束日期", endDateTime);
        Duration duration = Duration.between(beginDateTime, endDateTime);
        results.put("duration", duration);
        results.put("开始日期和结束日期之间相隔天", duration.toDays());
        results.put("开始日期和结束日期之间相隔小时", duration.toHours());
        results.put("开始日期和结束日期之间相隔分钟", duration.toMinutes());
        results.put("开始日期和结束日期之间相隔毫秒", duration.toMillis());
        results.put("开始日期和结束日期之间相隔纳秒", duration.toNanos());
        return Rest.ok(results);
    }

    @GetMapping("/monthDay")
    @ApiOperation(value = "MonthDay", notes = "MonthDay测试", produces = "application/json")
    public Rest<Map<String, Object>> monthDay() {
        MonthDay monthDay = MonthDay.now();
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("monthDay", monthDay);
        results.put("当前月份", monthDay.getMonth());
        results.put("当前月份值", monthDay.getMonthValue());
        results.put("当前天是当前月的第几天", monthDay.getDayOfMonth());
        Arrays.stream(values()).filter(monthDay::isSupported)
                .forEach(chronoField -> {
                    results.put(chronoField.name(), monthDay.getLong(chronoField));
                    results.put(chronoField.name() + ".range", monthDay.range(chronoField));
                });
        return Rest.ok(results);
    }

    @GetMapping("/yearMonth")
    @ApiOperation(value = "YearMonth", notes = "YearMonth测试", produces = "application/json")
    public Rest<Map<String, Object>> yearMonth() {
        YearMonth yearMonth = YearMonth.now();
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("yearMonth", yearMonth);
        results.put("当前年", yearMonth.getYear());
        results.put("当前月", yearMonth.getMonth());
        results.put("当前月份值", yearMonth.getMonthValue());
        Arrays.stream(values()).filter(yearMonth::isSupported)
                .forEach(chronoField -> {
                    results.put(chronoField.name(), yearMonth.getLong(chronoField));
                    results.put(chronoField.name() + ".range", yearMonth.range(chronoField));
                });
        return Rest.ok(results);
    }
}
