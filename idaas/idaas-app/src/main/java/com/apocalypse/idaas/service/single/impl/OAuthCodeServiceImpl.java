package com.apocalypse.idaas.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.OAuthCodeMapper;
import com.apocalypse.idaas.module.single.OAuthCode;
import com.apocalypse.idaas.service.single.OAuthCodeService;
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
public class OAuthCodeServiceImpl extends BaseServiceImpl<OAuthCode, Long> implements OAuthCodeService {

    @Resource
    private OAuthCodeMapper oauthCodeMapper;

}
