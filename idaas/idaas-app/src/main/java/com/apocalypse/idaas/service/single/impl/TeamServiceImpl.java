package com.gkjx.saas.health.system.service.single.impl;

import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.mapper.single.TeamMapper;
import com.gkjx.saas.health.system.model.single.Team;
import com.gkjx.saas.health.system.service.single.TeamService;
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
public class TeamServiceImpl extends BaseServiceImpl<Team, Long> implements TeamService {

    @Resource
    private TeamMapper teamMapper;

}
