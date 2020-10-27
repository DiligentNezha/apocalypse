package com.apocalypse.idaas.service.single.impl;

import com.apocalypse.common.data.mybatis.service.impl.BaseServiceImpl;
import com.apocalypse.idaas.mapper.single.OAuthRefreshTokenMapper;
import com.apocalypse.idaas.module.single.OAuthRefreshToken;
import com.apocalypse.idaas.service.single.OAuthRefreshTokenService;
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
public class OAuthRefreshTokenServiceImpl extends BaseServiceImpl<OAuthRefreshToken, Long> implements OAuthRefreshTokenService {

    @Resource
    private OAuthRefreshTokenMapper oAuthRefreshTokenMapper;

}
