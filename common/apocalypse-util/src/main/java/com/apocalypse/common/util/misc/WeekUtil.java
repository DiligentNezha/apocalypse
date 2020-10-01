package com.apocalypse.common.data.mybatis.util.misc;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2020/3/30
 */
public class WeekUtil {
    private static final Map<String, String> WEEK_MAP = new ConcurrentHashMap<>(128);

    static {
        int currentYear = LocalDate.now().getYear();
        init(currentYear);
    }

    public static String weekRange(Date date) {
        return weekRange(weekOfYear(date));
    }

    /**
     * 根据年和周获取该周的第一天和最后一天拼接串，周一作为每个星期的第一天
     * @param yearWeek 2017-00 例如：2017年第一周
     * @return 对应该周的第一天和最后一天 2016-12-26~2017-01-01
     */
    public static String weekRange(String yearWeek) {
        String weekRange = WEEK_MAP.get(yearWeek);
        if (StrUtil.isEmpty(weekRange)) {
            String year = yearWeek.split("-")[0];
            init(Integer.valueOf(year));
            weekRange = WEEK_MAP.get(yearWeek);
        }
        return weekRange;
    }

    /**
     * 返回日期的年和周，形式为 年-周（周从0开始记），周一作为每个星期的第一天：例如：2018-00,2018年第一周，2019-12，2019年第13周
     * @param date
     * @return
     */
    public static String weekOfYear(Date date) {
        DateTime dateTime = DateUtil.date(date);
        LocalDate localDate = LocalDate.of(dateTime.year(), dateTime.month() + 1, dateTime.dayOfMonth());
        WeekFields iso = WeekFields.ISO;
        int week = localDate.get(iso.weekOfYear());
        return dateTime.year() + "-" + String.format("%02d", week);
    }

    private static void init(int year) {
        DateTime firstDayOfYear = DateUtil.parseDate(year + "-01-01");
        DateTime lastDayOfYear = DateUtil.parseDate(year + "-12-31");
        DateRange dates = new DateRange(firstDayOfYear, lastDayOfYear, DateField.DAY_OF_YEAR);
        WeekFields iso = WeekFields.ISO;
        for (DateTime date : dates) {
            LocalDate localDate = LocalDate.of(date.year(), date.month() + 1, date.dayOfMonth());
            int week = localDate.get(iso.weekOfYear());
            WEEK_MAP.put(date.year() + "-" + String.format("%02d", week),
                    DateUtil.beginOfWeek(date).toDateStr() + "~" + DateUtil.endOfWeek(date).toDateStr());
        }
    }
}
