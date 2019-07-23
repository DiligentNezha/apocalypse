package com.apocalypse.example.sharding;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/7/23
 */
public class MonthRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

    public static final String YEAR_MONTH_FORMATTER = "yyyy_MM";

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        Range<Date> valueRange = shardingValue.getValueRange();
        String logicTableName = shardingValue.getLogicTableName();
        Date beginDate = valueRange.lowerEndpoint();
        Date endDate = valueRange.upperEndpoint();
        List<String> results = new ArrayList<>();
        while (beginDate.getTime() <= endDate.getTime()) {
            results.add(logicTableName + "_" + DateUtil.format(beginDate, YEAR_MONTH_FORMATTER));
            beginDate = DateUtil.offsetMonth(beginDate, 1);
        }
        return results;
    }
}
