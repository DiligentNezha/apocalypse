package com.apocalypse.common.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/7/9
 */
@Data
@ApiModel
@Accessors(chain = true)
public class TreeNodeVO implements Serializable {

    @ApiModelProperty(value = "节点 ID（元素 ID）")
    private Long key;

    @ApiModelProperty(value = "节点名（元素名）")
    private String title;

    @ApiModelProperty(value = "排序值")
    private Integer orderNum;

    @ApiModelProperty(value = "子节点列表")
    List<TreeNodeVO> children;
}
