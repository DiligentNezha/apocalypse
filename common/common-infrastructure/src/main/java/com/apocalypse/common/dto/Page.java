package com.apocalypse.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author <a href="jingkaihui@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/4/11
 */
@Getter
@Setter
@Accessors(chain = true)
public class Page implements Serializable {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private Long total;
}
