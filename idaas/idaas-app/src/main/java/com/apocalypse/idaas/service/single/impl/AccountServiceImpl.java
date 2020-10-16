package com.gkjx.saas.health.system.service.single.impl;

import com.gkjx.common.data.mybatis.service.impl.BaseServiceImpl;
import com.gkjx.saas.health.system.mapper.single.AccountMapper;
import com.gkjx.saas.health.system.model.single.Account;
import com.gkjx.saas.health.system.service.single.AccountService;
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
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements AccountService {

    @Resource
    private AccountMapper accountMapper;

}
