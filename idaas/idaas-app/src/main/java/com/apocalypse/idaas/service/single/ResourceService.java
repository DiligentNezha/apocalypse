package com.apocalypse.idaas.service.single;

import com.apocalypse.common.data.mybatis.service.BaseService;
import com.apocalypse.idaas.module.single.Resource;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
public interface ResourceService extends BaseService<Resource, Long> {

    /**
     * 填充元素的 elements 属性
     * @param resource
     */
    void fillElements(Resource resource);
}
