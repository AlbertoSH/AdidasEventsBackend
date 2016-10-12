package com.github.albertosh.adidas.backend.persistence.authinfo;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.user.AuthInfo;
import com.github.albertosh.adidas.backend.persistence.core.IPersistenceRead;

@ImplementedBy(AuthInfoPersistenceRead.class)
public interface IAuthInfoPersistenceRead extends IPersistenceRead<AuthInfo> {
}
