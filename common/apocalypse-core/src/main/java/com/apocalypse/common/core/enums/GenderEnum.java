package com.apocalypse.common.core.enums;

import com.apocalypse.common.core.constraints.IntegerValuesReadable;
import com.apocalypse.common.core.constraints.StringValuesReadable;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jingkaihui
 * @date 2020/3/30
 * @mail kaihuijing@gmail.com
 */
@Getter
public enum GenderEnum implements StringValuesReadable, IntegerValuesReadable {

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

    /**
     * 数据库存储值
     */
    public Integer dbValue;

    /**
     * 显示值
     */
    public String displayValue;

    GenderEnum(Integer dbValue, String displayValue) {
        this.dbValue = dbValue;
        this.displayValue = displayValue;
    }

    @Override
    public List<Integer> readIntegerValues() {
        return Arrays.stream(values()).map(genderEnum -> genderEnum.dbValue).collect(Collectors.toList());
    }

    @Override
    public List<String> readStringValues() {
        return Arrays.stream(values()).map(genderEnum -> genderEnum.displayValue).collect(Collectors.toList());
    }
}
