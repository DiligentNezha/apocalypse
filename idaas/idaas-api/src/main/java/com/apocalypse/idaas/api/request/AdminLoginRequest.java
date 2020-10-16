package com.apocalypse.idaas.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/8/7
 */
@Data
@ApiModel
@Accessors(chain = true)
public class AdminLoginRequest implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "登录名", example = "admin1")
    private String loginName;

    @NotBlank
    @ApiModelProperty(value = "密码", example = "1234")
    private String password;
}
