package com.apocalypse.common.core.api;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/4/22
 */
@Data
@ApiModel(value = "公共响应类")
public abstract class BaseResponse implements Serializable{

}
