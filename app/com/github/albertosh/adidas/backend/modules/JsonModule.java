package com.github.albertosh.adidas.backend.modules;

import com.google.inject.AbstractModule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import play.inject.ApplicationLifecycle;
import play.libs.Json;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_TIME;

public class JsonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class).asEagerSingleton();
        bind(LocalDateSerializer.class).asEagerSingleton();
        bind(LocalDateTimeSerializer.class).asEagerSingleton();
        bind(LocalTimeSerializer.class).asEagerSingleton();
        bind(OffsetTimeSerializer.class).asEagerSingleton();
        bind(DurationSerializer.class).asEagerSingleton();
    }

    @Singleton
    public static class ObjectMapperProvider implements Provider<ObjectMapper> {

        private final ApplicationLifecycle lifecycle;

        @Inject
        public ObjectMapperProvider(ApplicationLifecycle lifecycle) {
            this.lifecycle = lifecycle;
        }

        @Override
        public ObjectMapper get() {
            ObjectMapper mapper = Json.newDefaultMapper()
                    .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            Json.setObjectMapper(mapper);
            lifecycle.addStopHook(() -> {
                Json.setObjectMapper(null);
                return CompletableFuture.completedFuture(null);
            });
            return mapper;
        }
    }

    @Singleton
    public static class LocalDateSerializer extends StdSerializer<LocalDate> {

        @Inject
        public LocalDateSerializer() {
            super(LocalDate.class);
            register(Json.mapper());
        }

        private ObjectMapper register(ObjectMapper mapper) {
            SimpleModule m = new SimpleModule();
            m.addSerializer(LocalDate.class, this);
            mapper.registerModule(m);
            return mapper;
        }

        @Override
        public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
            jgen.writeString(value.format(ISO_DATE));
        }
    }

    @Singleton
    public static class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

        @Inject
        public LocalDateTimeSerializer() {
            super(LocalDateTime.class);
            register(Json.mapper());
        }

        private ObjectMapper register(ObjectMapper mapper) {
            SimpleModule m = new SimpleModule();
            m.addSerializer(LocalDateTime.class, this);
            mapper.registerModule(m);
            return mapper;
        }

        @Override
        public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
            jgen.writeString(value.format(ISO_DATE_TIME));
        }
    }

    @Singleton
    public static class LocalTimeSerializer extends StdSerializer<LocalTime> {

        @Inject
        public LocalTimeSerializer() {
            super(LocalTime.class);
            register(Json.mapper());
        }

        private ObjectMapper register(ObjectMapper mapper) {
            SimpleModule m = new SimpleModule();
            m.addSerializer(LocalTime.class, this);
            mapper.registerModule(m);
            return mapper;
        }

        @Override
        public void serialize(LocalTime value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
            jgen.writeString(value.format(ISO_OFFSET_TIME));
        }
    }

    @Singleton
    public static class OffsetTimeSerializer extends StdSerializer<OffsetTime> {

        @Inject
        public OffsetTimeSerializer() {
            super(OffsetTime.class);
            register(Json.mapper());
        }

        private ObjectMapper register(ObjectMapper mapper) {
            SimpleModule m = new SimpleModule();
            m.addSerializer(OffsetTime.class, this);
            mapper.registerModule(m);
            return mapper;
        }

        @Override
        public void serialize(OffsetTime value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
            jgen.writeString(value.format(ISO_OFFSET_TIME));
        }
    }

    @Singleton
    public static class DurationSerializer extends StdSerializer<Duration> {

        @Inject
        public DurationSerializer() {
            super(Duration.class);
            register(Json.mapper());
        }

        private ObjectMapper register(ObjectMapper mapper) {
            SimpleModule m = new SimpleModule();
            m.addSerializer(Duration.class, this);
            mapper.registerModule(m);
            return mapper;
        }

        @Override
        public void serialize(Duration value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
            jgen.writeString(value.toString());
        }
    }
}
