package com.apocalypse.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 景凯辉
 * @date 2018/11/10
 * @mail kaihuijing@gmail.com
 */
public enum DeletedStatusEnum {

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
        DeletedStatusEnum[] values = values();
        for (DeletedStatusEnum value : values) {
            map.put(value.value, value.name);
        }
    }

    /**
     * 状态名
     */
    public String name;

    /**
     * 状态值
     */
    public Integer value;

    DeletedStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static String getValue(Integer value) {
        return map.get(value);
    }

    public static boolean existValue(Integer value) {
        return map.containsKey(value);
    }
}
