package com.apocalypse.common.mybatis;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import tk.mybatis.mapper.genid.GenId;

public class MyGenId implements GenId<Integer> {
    private static final Snowflake GENERATOR = IdUtil.createSnowflake(1, 1);

    @Override
    public Integer genId(String table, String column) {
        return RandomUtil.randomInt(1, 10000);
    }

    public static String nextProductNo() {
        return "p" + String.valueOf(GENERATOR.nextId());
    }

    public static String nextOrderNo() {
        return "o" + String.valueOf(GENERATOR.nextId());
    }

    public static String nextBillNo() {
        return "b" + String.valueOf(GENERATOR.nextId());
    }
}
