package com.apocalypse.example.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/11
 */
@Data
public class ExampleVO implements Serializable {

    @ApiModelProperty(value = "Id")
    private Long id;

    private Long exampleExtendId;

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
     * 是否删除：1：已删除；0：未删除
     */
    @ApiModelProperty(value = "逻辑删除")
    private Integer deleted;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 爱好
     */
    @ApiModelProperty(value = "爱好", dataType = "com.alibaba.fastjson.JSONArray")
    private JSONArray hobby;

    /**
     * 扩展
     */
    @ApiModelProperty(value = "扩展数据", dataType = "com.alibaba.fastjson.JSONObject")
    private JSONObject extend;
}
