package com.github.albertosh.adidas.backend.persistence.event;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.event.MultilingualEvent;
import com.github.albertosh.adidas.backend.persistence.core.IPersistenceCreate;

@ImplementedBy(MultilingualEventPersistenceCreate.class)
public interface IMultilingualEventPersistenceCreate extends IPersistenceCreate<MultilingualEvent> {
}
