package com.apocalypse.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/5/31
 */
@Data
@Accessors(chain = true)
public class UserRegisterDTO implements Serializable {

    /**
     * 昵称
     */
    @NotBlank
    private String nickname;

    /**
     * 手机号
     */
    @NotBlank
    private String phone;
}
