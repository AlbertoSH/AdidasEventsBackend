package com.github.albertosh.adidas.backend.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import com.mongodb.ServerAddress;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;

import org.bson.codecs.configuration.CodecRegistry;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import javax.inject.Named;
import javax.inject.Singleton;

import play.Configuration;
import play.Logger;
import play.inject.ApplicationLifecycle;

public class PersistenceModule extends AbstractModule {

    private final static String DB_HOST = "db.default.host";
    private final static String DEFAULT_DB_HOST = "127.0.0.1";
    private final static String DB_PORT = "db.default.port";
    private final static Integer DEFAULT_DB_PORT = 27017;
    private final static String DB_NAME = "db.default.name";
    private final static String DEFAULT_DB_NAME = "adidas";

    private final static String BASE_PATH = "storagePath.base";
    private final static String DEFAULT_BASE_PATH = "/data";

    @Override
    protected void configure() {
    }

    @Provides
    @Named("storageBasePath")
    File providesStorageBasePath(Configuration configuration, Logger.ALogger logger) {
        String basePath = configuration.getString(BASE_PATH);
        if (basePath == null) {
            logger.warn("Path at " + BASE_PATH + " not found! Using " + DEFAULT_BASE_PATH);
            basePath = DEFAULT_BASE_PATH;
        }
        return new File(basePath);
    }

    @Provides
    @Singleton
    MongoClient providesMongoClient(ApplicationLifecycle lifecycle, MongoClientSettings settings) {
        MongoClient client = MongoClients.create(settings);
        lifecycle.addStopHook(() -> {
            client.close();
            return CompletableFuture.completedFuture(null);
        });
        return client;
    }

    @Provides
    @Named("dbName")
    String providesdbName(Configuration configuration, Logger.ALogger logger) {
        String name = configuration.getString(DB_NAME);
        if (name == null) {
            logger.warn("Name at " + DB_NAME + " not found! Using " + DEFAULT_DB_NAME);
            name = DEFAULT_DB_NAME;
        }
        return name;
    }

    @Provides
    @Named("dbHost")
    String providesHost(Configuration configuration, Logger.ALogger logger) {
        String host = configuration.getString(DB_HOST);
        if (host == null) {
            logger.warn("Host at " + DB_HOST + " not found! Using " + DEFAULT_DB_HOST);
            host = DEFAULT_DB_HOST;
        }
        return host;
    }

    @Provides
    @Named("dbPort")
    Integer providesPort(Configuration configuration, Logger.ALogger logger) {
        Integer port = configuration.getInt(DB_PORT);
        if (port == null) {
            logger.warn("Port at " + DB_PORT + " not found! Using " + DEFAULT_DB_PORT);
            port = DEFAULT_DB_PORT;
        }
        return port;
    }

    @Provides
    @Singleton
    MongoClientSettings providesMongoSettings(CodecRegistry codecRegistry, ClusterSettings clusterSettings) {
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .clusterSettings(clusterSettings)
                .build();
        return settings;
    }


    @Provides
    @Singleton
    ClusterSettings providesMongoCluster(@Named("dbHost") String host, @Named("dbPort") Integer port) {
        return ClusterSettings.builder()
                .hosts(Collections.singletonList(new ServerAddress(host, port)))
                .build();
    }
}
