package com.apocalypse.common.model;

import javax.persistence.Id;
import java.util.UUID;

public class UUIDKeysModel implements BaseModel {

    @Id
    private String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
