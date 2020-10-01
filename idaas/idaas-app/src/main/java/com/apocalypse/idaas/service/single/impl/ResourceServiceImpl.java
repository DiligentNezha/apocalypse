package com.apocalypse.idaas.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.ResourceDOMapper;
import com.apocalypse.idaas.model.ResourceDO;
import com.apocalypse.idaas.service.single.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @Description
 * @date 2019/6/10
 */
@Slf4j
@Service
public class ResourceServiceImpl extends BaseServiceImpl<ResourceDO, Integer> implements ResourceService {

    @Autowired
    private ResourceDOMapper ResourceDOMapper;

}
