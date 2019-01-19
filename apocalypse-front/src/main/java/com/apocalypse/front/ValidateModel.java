package com.apocalypse.front;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class ValidateModel {

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;
}
