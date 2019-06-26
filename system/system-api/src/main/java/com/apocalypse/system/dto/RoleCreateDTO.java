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
public class RoleCreateDTO implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "角色名", required = true, example = "店小二")
    private String name;
}
