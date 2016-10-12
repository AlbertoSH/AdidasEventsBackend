package com.github.albertosh.adidas.backend.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Singleton;

import play.Logger;

public class UtilsModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    Logger.ALogger logger() {
        return Logger.of("application");
    }
}
