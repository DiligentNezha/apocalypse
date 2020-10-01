package com.apocalypse.common.core.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2020/3/30
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel
public class Page implements Serializable {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", example = "1")
    private Integer current = 1;

    /**
     * 每页数据条数
     */
    @ApiModelProperty(value = "每页数据条数", example = "10")
    private Integer size = 10;

    /**
     * 总数据条数
     */
    @ApiModelProperty(value = "总数据条数", example = "1024")
    private Long total = 0L;
}
