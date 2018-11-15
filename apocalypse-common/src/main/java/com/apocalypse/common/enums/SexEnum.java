package com.apocalypse.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 景凯辉
 * @date 2018/11/10
 * @mail jingkaihui@adpanshi.com
 */
public enum SexEnum {

    /**
     * 女性
     */
    F("F", (byte)0,"女"),

    /**
     * 男性
     */
    M("M", (byte)1, "男"),

    /**
     * 保密
     */
    S("S", (byte)2, "保密");

    private static final Map<String, Byte> map = new HashMap<>();

    static {
        map.put(SexEnum.F.code, SexEnum.F.value);
        map.put(SexEnum.M.code, SexEnum.M.value);
        map.put(SexEnum.S.code, SexEnum.S.value);
    }

    /**
     * code 码， M为男性，F为女性，S为保密
     */
    private String code;

    /**
     * 数据库当中存储的值
     */
    private Byte value;

    /**
     * 性别描述
     */
    private String desc;

    SexEnum(String code, Byte value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根据code获取对应的value
     * @param code code串
     * @return value
     */
    public static Byte getValue(String code) {
        return map.get(code);
    }

}
