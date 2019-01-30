package com.apocalypse.front;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ValidateModel {

    @NotNull
    @ApiModelProperty(name = "name", example = "小明")
    private String name;

    @NotNull
    @Length(min = 5, max = 10)
    @ApiModelProperty(name = "password", example = "123456")
    private String password;

    @NotNull
    @Email
    @ApiModelProperty(name = "email", example = "1234@qq.com")
    private String email;
}
