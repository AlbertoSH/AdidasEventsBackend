package com.github.albertosh.adidas.backend.persistence.codecs;

import com.github.albertosh.adidas.backend.persistence.core.ObjectWithId;

import org.apache.commons.lang3.tuple.Pair;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public abstract class ObjectWithIdCodec<T extends ObjectWithId> implements Codec<T> {

    @Inject
    protected ObjectWithIdCodec() {
    }

    @Override
    public final T decode(BsonReader bsonReader, DecoderContext decoderContext) {
        ObjectWithId.Builder<T> builder = getBuilder();
        bsonReader.readStartDocument();
        builder.id(bsonReader.readObjectId("_id").toString());

        decodeCurrentObjectTypeFields(bsonReader, decoderContext, builder);

        bsonReader.readEndDocument();
        return builder.build();
    }

    protected abstract <B extends ObjectWithId.Builder<T>> B getBuilder();

    private <B extends ObjectWithId.Builder<T>> void decodeCurrentObjectTypeFields(BsonReader bsonReader, DecoderContext decoderContext, B builder) {
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String field = bsonReader.readName();
            decodeCurrentValue(bsonReader, decoderContext, builder, field);
        }
    }

    protected <B extends ObjectWithId.Builder<T>> void decodeCurrentValue(BsonReader reader, DecoderContext decoderContext, B builder, String field) {}

    @Override
    public final void encode(BsonWriter bsonWriter, T value, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeObjectId("_id", new ObjectId(value.getId()));

        encodeCurrentObjectTypeFields(bsonWriter, value, encoderContext);

        bsonWriter.writeEndDocument();
    }

    protected void encodeCurrentObjectTypeFields(BsonWriter writer, T value, EncoderContext context) {}

}
