package com.github.albertosh.adidas.backend.persistence.user;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.user.User;
import com.github.albertosh.adidas.backend.persistence.core.IPersistenceRead;

@ImplementedBy(UserPersistenceRead.class)
public interface IUserPersistenceRead extends IPersistenceRead<User> {
}
