package com.apocalypse.common.core.api;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="kaihuijing@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/4/22
 */
@Data
@ApiModel(value = "公共请求类")
public abstract class BaseRequest implements Serializable {

}
