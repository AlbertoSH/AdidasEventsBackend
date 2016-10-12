package com.github.albertosh.adidas.backend.persistence.user;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.user.User;
import com.github.albertosh.adidas.backend.persistence.core.IPersistenceUpdate;

@ImplementedBy(UserPersistenceUpdate.class)
public interface IUserPersistenceUpdate extends IPersistenceUpdate<User> {
}
