package com.apocalypse.example.dto;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
public class ExampleModifyDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "Id")
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", example = "江阿生")
    private String name;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日", example = "2019-05-31")
    private LocalDate birthday;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "低调！低调！低调！重要的事情说三遍！")
    private String remark;

    /**
     * 爱好
     */
    @ApiModelProperty(value = "爱好")
    private ArrayNode hobby;

    /**
     * 扩展
     */
    @ApiModelProperty(value = "扩展数据")
    private ObjectNode extend;
}
