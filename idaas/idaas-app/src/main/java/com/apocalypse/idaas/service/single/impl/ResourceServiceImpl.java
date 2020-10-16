package com.gkjx.saas.health.system.service.single.impl;

import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.mapper.single.ElementMapper;
import com.gkjx.saas.health.system.mapper.single.ResourceMapper;
import com.gkjx.saas.health.system.model.single.Element;
import com.gkjx.saas.health.system.model.single.Resource;
import com.gkjx.saas.health.system.service.single.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource, Long> implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ElementMapper elementMapper;

    @Override
    public void fillElements(Resource resource) {
        List<Element> elements = elementMapper.selectByProperty(Element::getResourceId, resource.getId());
        resource.setElements(elements);
    }
}
