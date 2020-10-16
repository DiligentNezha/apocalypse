package com.gkjx.saas.health.system.api.request;

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

    @NotNull
    @ApiModelProperty(value = "机构 ID", hidden = true, example = "1293385365155090432")
    private Long organId;

    @NotBlank
    @ApiModelProperty(value = "登录名", example = "admin1")
    private String loginName;

    @NotBlank
    @ApiModelProperty(value = "密码", example = "1234")
    private String password;

    @NotBlank
    @ApiModelProperty(value = "验证码 ID", required = true, example = "fe544239-c68d-4838-a91c-6d7dae0bb88d")
    private String uuid = "";

    @NotBlank
    @ApiModelProperty(value = "验证码", required = true, example = "vfj4")
    private String captcha = "";
}
