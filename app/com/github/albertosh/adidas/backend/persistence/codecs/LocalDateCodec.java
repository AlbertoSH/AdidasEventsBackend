package com.github.albertosh.adidas.backend.persistence.codecs;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import javax.inject.Singleton;

@Singleton
public class LocalDateCodec implements Codec<LocalDate> {

    @Override
    public LocalDate decode(BsonReader reader, DecoderContext decoderContext) {
        Date date = new Date(reader.readDateTime());
        Instant instant = date.toInstant();
        return instant.atOffset(ZoneOffset.UTC).toLocalDate();
    }

    @Override
    public void encode(BsonWriter writer, LocalDate value, EncoderContext encoderContext) {
        writer.writeDateTime(value.atStartOfDay().atOffset(ZoneOffset.UTC).toInstant().toEpochMilli());
    }

    @Override
    public Class<LocalDate> getEncoderClass() {
        return LocalDate.class;
    }

}
