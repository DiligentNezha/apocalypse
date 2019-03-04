package com.apocalypse.example.service.simple.impl;

import com.apocalypse.common.service.impl.BaseServiceImpl;
import com.apocalypse.example.mapper.simple.DemoModelMapper;
import com.apocalypse.example.model.DemoModel;
import com.apocalypse.example.service.simple.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 景凯辉
 * @date 2018/11/9
 * @mail kaihuijing@gmail.com
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class DemoServiceImpl extends BaseServiceImpl<DemoModel> implements DemoService {

    @Autowired
    private DemoModelMapper demoModelMapper;

}
