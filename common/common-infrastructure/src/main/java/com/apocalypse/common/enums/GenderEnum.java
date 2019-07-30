package com.apocalypse.common.enums;

import com.apocalypse.common.constraints.IntegerValuesReadable;
import com.apocalypse.common.constraints.StringValuesReadable;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 景凯辉
 * @date 2018/11/10
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
     * 状态码
     */
    public Integer code;

    /**
     * 状态值
     */
    public String value;

    GenderEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public List<Integer> readIntegerValues() {
        return Arrays.stream(values()).map(genderEnum -> genderEnum.code).collect(Collectors.toList());
    }

    @Override
    public List<String> readStringValues() {
        return Arrays.stream(values()).map(genderEnum -> genderEnum.value).collect(Collectors.toList());
    }
}
