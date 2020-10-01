package com.apocalypse.common.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jingkaihui
 * @date 2020/3/30
 * @mail kaihuijing@gmail.com
 */
public enum EnabledEnum {

    /**
     * 已启用
     */
    ENABLED(1, "是"),

    /**
     * 已禁用
     */
    DISABLED(0, "否"),

    ;
    private static final Map<Integer, String> map = new HashMap<>();

    static {
        EnabledEnum[] values = values();
        for (EnabledEnum value : values) {
            map.put(value.dbValue, value.displayValue);
        }
    }

    /**
     * 显示值
     */
    public String displayValue;

    /**
     * 数据库存储值
     */
    public Integer dbValue;

    EnabledEnum(Integer dbValue, String displayValue) {
        this.dbValue = dbValue;
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public Integer getDbValue() {
        return dbValue;
    }

    public void setDbValue(Integer dbValue) {
        this.dbValue = dbValue;
    }

    public static String getValue(Integer value) {
        return map.get(value);
    }

    public static boolean existValue(Integer value) {
        return map.containsKey(value);
    }
}
