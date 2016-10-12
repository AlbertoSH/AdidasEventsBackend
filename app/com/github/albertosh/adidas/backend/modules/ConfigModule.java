package com.github.albertosh.adidas.backend.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Named;

import play.Configuration;

public class ConfigModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Named("defaultLanguage")
    String defaultLanguage(Configuration configuration) {
        return configuration.getString("application.langs");
    }
}
