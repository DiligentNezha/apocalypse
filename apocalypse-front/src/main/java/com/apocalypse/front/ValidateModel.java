package com.apocalypse.front;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ValidateModel {

    @NotNull
    private String name;

    @NotNull
    @Length(min = 5, max = 10)
    private String password;

    @NotNull
    @Email
    private String email;
}
