package com.apocalypse.example.sharding;

import cn.hutool.core.date.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/22
 */
public class MonthPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    public static final String YEAR_MONTH_FORMATTER = "yyyy_MM";

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        String logicTableName = shardingValue.getLogicTableName();
        Date date = shardingValue.getValue();
        return logicTableName + "_" + DateUtil.format(date, YEAR_MONTH_FORMATTER);
    }
}
