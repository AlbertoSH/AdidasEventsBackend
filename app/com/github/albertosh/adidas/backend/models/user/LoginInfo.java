package com.github.albertosh.adidas.backend.models.user;

import com.github.albertosh.swagplash.annotations.ApiModel;
import com.github.albertosh.swagplash.annotations.ApiModelProperty;

@ApiModel
public class LoginInfo {

    @ApiModelProperty
    private final String uuid;
    @ApiModelProperty
    private final String token;

    public LoginInfo(AuthInfo authInfo) {
        this.uuid = authInfo.getUuid();
        this.token = authInfo.getToken();
    }

    public String getUuid() {
        return uuid;
    }

    public String getToken() {
        return token;
    }

}
