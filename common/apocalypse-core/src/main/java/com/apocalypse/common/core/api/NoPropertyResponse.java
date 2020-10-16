package com.apocalypse.common.core.api;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description 该类用于没有属性返回时作为解除泛型语法的限制而用
 * @date 2020/4/22
 */
@Data
@ApiModel(value = "无属性响应类")
public class NoPropertyResponse extends BaseResponse{

}
