package com.apocalypse.idaas.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.TeamIdentityUnionMapper;
import com.apocalypse.idaas.module.single.TeamIdentityUnion;
import com.apocalypse.idaas.service.single.TeamIdentityUnionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class TeamIdentityUnionServiceImpl extends BaseServiceImpl<TeamIdentityUnion, Long> implements TeamIdentityUnionService {

    @Resource
    private TeamIdentityUnionMapper teamIdentityUnionMapper;

}
