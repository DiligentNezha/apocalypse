package com.apocalypse.idaas.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.IdentityAccountUnionMapper;
import com.apocalypse.idaas.module.single.IdentityAccountUnion;
import com.apocalypse.idaas.service.single.IdentityAccountUnionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class IdentityAccountUnionServiceImpl extends BaseServiceImpl<IdentityAccountUnion, Long> implements IdentityAccountUnionService {

    @Resource
    private IdentityAccountUnionMapper identityAccountUnionMapper;

}
