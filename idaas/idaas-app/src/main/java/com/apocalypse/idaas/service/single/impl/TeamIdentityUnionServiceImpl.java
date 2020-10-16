package com.gkjx.saas.health.system.service.single.impl;

import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.mapper.single.TeamIdentityUnionMapper;
import com.gkjx.saas.health.system.model.single.TeamIdentityUnion;
import com.gkjx.saas.health.system.service.single.TeamIdentityUnionService;
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
public class TeamIdentityUnionServiceImpl extends BaseServiceImpl<TeamIdentityUnion, Long> implements TeamIdentityUnionService {

    @Resource
    private TeamIdentityUnionMapper teamIdentityUnionMapper;

}
