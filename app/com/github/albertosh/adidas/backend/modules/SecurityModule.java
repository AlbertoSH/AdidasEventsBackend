package com.github.albertosh.adidas.backend.modules;

import com.github.albertosh.adidas.backend.security.AdminAuthorizationCheck;
import com.github.albertosh.adidas.backend.security.TokenAuthorizationCheck;
import com.github.albertosh.swagplash.authorization.AuthorizationCheck;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import play.Configuration;
import play.Logger;
import play.mvc.Http;

import javax.inject.Named;

public class SecurityModule extends AbstractModule {

    private final static String HEADER_KEY = "security.header";
    private final static String DEFAULT_HEADER = Http.HeaderNames.AUTHORIZATION;

    private final static String HEADER_VALUE = "security.value";
    private final static String DEFAULT_VALUE = "impossibleIsNothing";

    @Override
    protected void configure() {
        bind(AuthorizationCheck.class).to(TokenAuthorizationCheck.class);
    }


    @Provides
    @Named("authorizationHeader") String authorizationHeader(Configuration conf, Logger.ALogger logger) {
        String header = conf.getString(HEADER_KEY);
        if (header == null) {
            logger.warn("Authorization header at " + HEADER_KEY + " not found! Using " + DEFAULT_HEADER);
            header = DEFAULT_HEADER;
        }
        return header;
    }

    @Provides
    @Named("authorizationValue") String authorizationValue(Configuration conf, Logger.ALogger logger) {
        String header = conf.getString(HEADER_VALUE);
        if (header == null) {
            logger.warn("Authorization value at " + HEADER_VALUE + " not found! Using " + DEFAULT_VALUE);
            header = DEFAULT_VALUE;
        }
        return header;
    }


}
