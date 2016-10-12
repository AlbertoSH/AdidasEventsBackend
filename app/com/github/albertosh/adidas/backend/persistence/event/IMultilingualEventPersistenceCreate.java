package com.github.albertosh.adidas.backend.persistence.event;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.MultilingualEvent;
import com.github.albertosh.adidas.backend.persistence.core.IPersistenceCreate;
import com.github.albertosh.adidas.backend.persistence.core.IPersistenceRead;

@ImplementedBy(MultilingualEventPersistenceCreate.class)
public interface IMultilingualEventPersistenceCreate extends IPersistenceCreate<MultilingualEvent> {
}
