package com.apocalypse.example.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jingkaihui
 * @date 2019/2/11
 */
@Getter
@Setter
public class LoginInfoDTO implements Serializable {

    @ApiModelProperty(value = "name", example = "小王")
    private String name;

    @ApiModelProperty(value = "realName", example = "小明")
    private String realName;

    @NotNull
    @ApiModelProperty(value = "mail", example = "abc@qq.com")
    private String mail;

    @ApiModelProperty(value = "password", example = "123456")
    @NotNull
    private String password;

}
