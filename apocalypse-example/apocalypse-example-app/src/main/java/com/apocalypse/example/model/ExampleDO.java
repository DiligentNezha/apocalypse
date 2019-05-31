package com.apocalypse.example.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.mybatis.SnowflakeIdGenId;
import com.apocalypse.common.mybatis.type.JSONArrayTypeHandler;
import com.apocalypse.common.mybatis.type.JSONObjectTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.ColumnType;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.annotation.LogicDelete;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
@Data
@Accessors(chain = true)
@Table(name = "example")
public class ExampleDO implements Serializable {
    /**
     * 编号
     */
    @Id
    @KeySql(genId = SnowflakeIdGenId.class)
    private Long id;

    @Column(name = "example_extend_id")
    private Long exampleExtendId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    @LogicDelete(notDeletedValue = 0, isDeletedValue = 1)
    private Integer deleted;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 爱好
     */
    @ColumnType(typeHandler = JSONArrayTypeHandler.class)
    private JSONArray hobby;

    /**
     * 扩展
     */
    @ColumnType(typeHandler = JSONObjectTypeHandler.class)
    private JSONObject extend;
}