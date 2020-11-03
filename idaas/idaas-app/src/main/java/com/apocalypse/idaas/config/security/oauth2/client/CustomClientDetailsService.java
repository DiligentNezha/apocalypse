package com.apocalypse.idaas.config.security.oauth2.client;

import com.apocalypse.idaas.convert.OAuth2ClientDetailsConverter;
import com.apocalypse.idaas.module.single.OAuthClientDetails;
import com.apocalypse.idaas.service.single.OAuthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/23
 */
@Primary
@Component()
public class CustomClientDetailsService implements ClientDetailsService {

    @Autowired
    private OAuthClientDetailsService authClientDetailsService;

    @Autowired
    private OAuth2ClientDetailsConverter authClientDetailsConverter;

    @Override
    public CustomClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OAuthClientDetails oAuthClientDetails = authClientDetailsService.selectOneByProperty(OAuthClientDetails::getClientId, clientId);
        CustomClientDetails customClientDetails = authClientDetailsConverter.convert(oAuthClientDetails);
        return customClientDetails;
    }

}
