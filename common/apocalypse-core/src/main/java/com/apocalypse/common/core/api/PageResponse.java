package com.apocalypse.common.core.api;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description 该类作为一个缓冲，用于一些接口没有参数的情况下
 * @date 2020/4/22
 */
@Data
@ApiModel(value = "分页响应类")
public abstract class PageResponse extends BaseResponse {
    /**
     * 分页数据
     */
    @ApiModelProperty(value = "分页信息", notes = "分页请求")
    private Page page = new Page();

    public int currentPage() {
        return ObjectUtil.isNull(page) ? 1 : page.getCurrent();
    }

    public int currentSize() {
        return ObjectUtil.isNull(page) ? 1 : page.getSize();
    }
}
