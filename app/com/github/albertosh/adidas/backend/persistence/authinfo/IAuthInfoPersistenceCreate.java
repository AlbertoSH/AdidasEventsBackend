package com.github.albertosh.adidas.backend.persistence.authinfo;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.user.AuthInfo;
import com.github.albertosh.adidas.backend.persistence.core.IPersistenceCreate;

@ImplementedBy(AuthInfoPersistenceCreate.class)
public interface IAuthInfoPersistenceCreate extends IPersistenceCreate<AuthInfo> {
}
