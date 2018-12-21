package com.apocalypse.example.model;

import com.alibaba.fastjson.JSONObject;
import com.apocalypse.common.mybatis.type.JsonTypeHandler;
import tk.mybatis.mapper.annotation.ColumnType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

@Table(name = "t_example_demo")
public class DemoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer sex;

    private LocalDate birthday;

    @ColumnType(typeHandler = JsonTypeHandler.class)
    private JSONObject extend;

    private String fcu;

    private LocalDateTime fcd;

    private String lmu;

    private LocalDateTime lmd;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return sex
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * @return birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * @return extend
     */
    public JSONObject getExtend() {
        return extend;
    }

    /**
     * @param extend
     */
    public void setExtend(JSONObject extend) {
        this.extend = extend;
    }

    /**
     * @return fcu
     */
    public String getFcu() {
        return fcu;
    }

    /**
     * @param fcu
     */
    public void setFcu(String fcu) {
        this.fcu = fcu;
    }

    /**
     * @return fcd
     */
    public LocalDateTime getFcd() {
        return fcd;
    }

    /**
     * @param fcd
     */
    public void setFcd(LocalDateTime fcd) {
        this.fcd = fcd;
    }

    /**
     * @return lmu
     */
    public String getLmu() {
        return lmu;
    }

    /**
     * @param lmu
     */
    public void setLmu(String lmu) {
        this.lmu = lmu;
    }

    /**
     * @return lmd
     */
    public LocalDateTime getLmd() {
        return lmd;
    }

    /**
     * @param lmd
     */
    public void setLmd(LocalDateTime lmd) {
        this.lmd = lmd;
    }
}