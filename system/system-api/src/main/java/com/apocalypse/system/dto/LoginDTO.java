package com.apocalypse.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/12
 */
@Data
@ApiModel
public class LoginDTO implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "邮箱", required = true, example = "499508968@qq.com")
    private String mail;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true, example = "123456")
    private String password;
}
