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
    MALE("男", 1),

    /**
     * 女性
     */
    FEMALE("女", 2),

    /**
     * 保密
     */
    SECRET("保密", 3);

    private static final Map<Integer, String> map = new HashMap<>();

    static {
        map.put(GenderEnum.MALE.value, GenderEnum.MALE.view);
        map.put(GenderEnum.FEMALE.value, GenderEnum.FEMALE.view);
        map.put(GenderEnum.SECRET.value, GenderEnum.SECRET.view);
    }

    /**
     * view 展示层
     */
    public String view;

    /**
     * 数据库当中存储的值
     */
    public Integer value;

    GenderEnum(String view, Integer value) {
        this.view = view;
        this.value = value;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * 根据value获取对应的view
     * @param value 数据库中存储的值
     * @return value 视图层展示的数据
     */
    public static String getValue(Integer value) {
        return map.get(value);
    }

    public static boolean existValue(Integer value) {
        return map.containsKey(value);
    }
}
