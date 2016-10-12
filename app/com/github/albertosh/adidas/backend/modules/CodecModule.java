package com.github.albertosh.adidas.backend.modules;

import com.google.common.reflect.ClassPath;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import com.mongodb.async.client.MongoClients;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import play.Application;

public class CodecModule extends AbstractModule {

    @Override
    protected void configure() {
    }


    @Provides
    @Singleton
    CodecRegistry providesCodecRegistry(Application application) throws IOException {

        Set<ClassPath.ClassInfo> codecsInfo =
                ClassPath.from(getClass().getClassLoader())
                        .getTopLevelClasses("com.github.albertosh.adidas.backend.persistence.codecs");

        List<Codec<?>> codecs = new ArrayList<>();
        for (ClassPath.ClassInfo codecInfo : codecsInfo) {
            if (!codecInfo.getName().contains("Codec"))
                continue;

            Class<Codec<?>> codecClass = (Class<Codec<?>>) codecInfo.load();
            if (!Modifier.isAbstract(codecClass.getModifiers())) {
                Codec<?> codec = application.injector().instanceOf(codecClass);
                codecs.add(codec);
            }
        }

        return CodecRegistries.fromRegistries(
                MongoClients.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(codecs)
        );

    }

}
