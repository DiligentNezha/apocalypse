package com.apocalypse.idaas.config.security.oauth2.client;

import com.apocalypse.idaas.config.security.userdetails.CustomUserDetails;
import com.apocalypse.idaas.convert.OAuth2ClientDetailsConverter;
import com.apocalypse.idaas.module.single.OAuthClientDetails;
import com.apocalypse.idaas.service.single.OAuthClientDetailsService;
import com.qiniu.util.Auth;
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
@Component
public class CustomClientDetailsService implements ClientDetailsService, ClientRegistrationService {

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

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {

    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {

    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {

    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {

    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return null;
    }


}
