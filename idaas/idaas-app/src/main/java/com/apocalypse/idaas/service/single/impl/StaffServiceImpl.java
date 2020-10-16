package com.apocalypse.idaas.service.single.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.apocalypse.common.core.util.ServiceExceptionUtil;
import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.IdentityMapper;
import com.apocalypse.idaas.mapper.single.StaffMapper;
import com.apocalypse.idaas.module.single.Identity;
import com.apocalypse.idaas.module.single.Staff;
import com.apocalypse.idaas.service.single.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author <a href="jingkaihui@guokejianxin.com">jingkaihui</a>
 * @description
 * @date 2020/6/8
 */
@Slf4j
@Service
public class StaffServiceImpl extends BaseServiceImpl<Staff, Long> implements StaffService {

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private IdentityMapper identityMapper;


}
