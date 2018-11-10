package com.apocalypse.common.enums;

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

}
