package com.apocalypse.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 景凯辉
 * @date 2018/11/10
 * @mail kaihuijing@gmail.com
 */
public enum GenderEnum {

    /**
     * 男性
     */
    MALE(1, "男"),

    /**
     * 女性
     */
    FEMALE(2, "女"),

    /**
     * 保密
     */
    SECRET(3, "保密");

    private static final Map<Integer, String> map = new HashMap<>();

    static {
        GenderEnum[] values = values();
        for (GenderEnum value : values) {
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

    GenderEnum(Integer value, String name) {
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
