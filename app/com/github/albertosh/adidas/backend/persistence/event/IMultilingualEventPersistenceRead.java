package com.github.albertosh.adidas.backend.persistence.event;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.event.MultilingualEvent;
import com.github.albertosh.adidas.backend.persistence.core.IPersistenceRead;

@ImplementedBy(MultilingualEventPersistenceRead.class)
public interface IMultilingualEventPersistenceRead extends IPersistenceRead<MultilingualEvent> {
}
