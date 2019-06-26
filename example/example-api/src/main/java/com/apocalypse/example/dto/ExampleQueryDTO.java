package com.apocalypse.example.dto;

import com.apocalypse.common.constraints.InList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/11
 */
@Data
public class ExampleQueryDTO implements Serializable {

    /**
     * 姓名
     */
    @InList(options = {"江阿生", "曾静"})
    @ApiModelProperty(value = "姓名", example = "江阿生")
    private String name;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", example = "2019-05-31")
    private LocalDate birthday;
}
