package com.apocalypse.common.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jingkaihui
 * @date 2020/3/30
 * @mail kaihuijing@gmail.com
 */
public enum DeleteEnum {

    /**
     * （正常）未删除
     */
    NOT_DELETED(0, "(正常)未删除"),

    /**
     * 已删除
     */
    DELETED(1, "已删除"),

    ;
    private static final Map<Integer, String> map = new HashMap<>();

    static {
        DeleteEnum[] values = values();
        for (DeleteEnum value : values) {
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

    DeleteEnum(Integer dbValue, String displayValue) {
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
