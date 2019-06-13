package com.apocalypse.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/13
 */
@Data
public class LoginVO implements Serializable {

    @ApiModelProperty(value = "邮箱", example = "499508968@qq.com")
    private String mail;

    @ApiModelProperty(value = "密码", example = "123456")
    private String nickName;
}

