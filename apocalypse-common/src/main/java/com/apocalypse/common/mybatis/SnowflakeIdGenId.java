package com.apocalypse.common.mybatis;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import tk.mybatis.mapper.genid.GenId;

public class SnowflakeIdGenId implements GenId<Long> {
    private static final Snowflake GENERATOR = IdUtil.createSnowflake(1, 1);

    @Override
    public Long genId(String table, String column) {
        return GENERATOR.nextId();
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
