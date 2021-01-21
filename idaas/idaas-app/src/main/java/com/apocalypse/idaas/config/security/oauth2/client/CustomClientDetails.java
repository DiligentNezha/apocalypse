package com.apocalypse.idaas.config.security.oauth2.client;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;

/**
 * @author <a href="kaihuijing@gmail.com">jingkaihui</a>
 * @description
 * @date 2020/10/23
 */
public class CustomClientDetails extends BaseClientDetails {

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端描述
     */
    private String description;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
