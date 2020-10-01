package com.apocalypse.example.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.data.mybatis.SnowflakeIdGenId;
import com.apocalypse.common.data.mybatis.type.JSONArrayTypeHandler;
import com.apocalypse.common.data.mybatis.type.JSONObjectTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@ApiModel
@Accessors(chain = true)
@Table(name = "example")
public class ExampleDO implements Serializable {
    /**
     * 编号
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    @ApiModelProperty(value = "Id")
    private Long id;

    @Column(name = "example_extend_id")
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
     * 是否删除（1：已删除；0：未删除）
     */
    @LogicDelete(notDeletedValue = 0, isDeletedValue = 1)
    @ApiModelProperty(value = "逻辑删除")
    private Integer deleted;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 爱好
     */
    @ColumnType(typeHandler = JSONArrayTypeHandler.class)
    @ApiModelProperty(value = "爱好", dataType = "com.alibaba.fastjson.JSONArray")
    private JSONArray hobby;

    /**
     * 扩展
     */
    @ColumnType(typeHandler = JSONObjectTypeHandler.class)
    @ApiModelProperty(value = "扩展数据", dataType = "com.alibaba.fastjson.JSONObject")
    private JSONObject extend;
}