package com.gkjx.saas.health.system.service.single;

import com.gkjx.common.data.mybatis.service.BaseService;
import com.gkjx.saas.health.system.model.single.Resource;

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
