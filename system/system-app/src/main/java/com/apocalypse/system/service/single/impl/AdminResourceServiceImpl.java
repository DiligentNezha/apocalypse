package com.apocalypse.system.service.single.impl;

import com.apocalypse.common.service.impl.BaseServiceImpl;
import com.apocalypse.system.mapper.single.AdminResourceDOMapper;
import com.apocalypse.system.model.AdminResourceDO;
import com.apocalypse.system.service.single.AdminResourceService;
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
public class AdminResourceServiceImpl extends BaseServiceImpl<AdminResourceDO, Integer> implements AdminResourceService {

    @Autowired
    private AdminResourceDOMapper AdminResourceDOMapper;

}
