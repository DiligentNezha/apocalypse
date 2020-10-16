package com.gkjx.saas.health.system.service.single;

import com.gkjx.common.data.mybatis.service.BaseService;
import com.gkjx.saas.health.system.model.single.Element;

import java.util.List;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
public interface ElementService extends BaseService<Element, Long> {

    /**
     * 根据账户 ID 查找该账户拥有的所有元素并根据元素子父级关系构建菜单树结构
     * @param accountId 账户 ID
     * @return 菜单树结构
     */
    List<Element> buildElementTreeByAccountId(Long accountId);

    /**
     * 填充元素的 resource 属性
     * @param element
     */
    void fillResource(Element element);

}
