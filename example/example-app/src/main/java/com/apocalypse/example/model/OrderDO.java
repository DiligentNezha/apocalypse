package com.apocalypse.example.model;

import com.apocalypse.common.mybatis.MyGenId;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@Table(name = "t_order")
public class OrderDO implements Serializable {

    @Id
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "user_id")
    private Integer userId;

    private String status;

}
