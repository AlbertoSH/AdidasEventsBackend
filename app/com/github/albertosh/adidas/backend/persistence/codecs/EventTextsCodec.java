package com.github.albertosh.adidas.backend.persistence.codecs;

import com.github.albertosh.adidas.backend.models.EventTexts;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EventTextsCodec implements Codec<EventTexts> {

    @Inject
    public EventTextsCodec(LocalDateCodec localDateCodec) {
    }

    @Override
    public EventTexts decode(BsonReader reader, DecoderContext decoderContext) {
        EventTexts.Builder builder = new EventTexts.Builder();
        reader.readStartDocument();

        decodeCurrentObjectTypeFields(reader, decoderContext, builder);

        reader.readEndDocument();
        return builder.build();
    }

    private void decodeCurrentObjectTypeFields(BsonReader bsonReader, DecoderContext decoderContext,
                                               EventTexts.Builder builder) {
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String field = bsonReader.readName();
            decodeCurrentValue(bsonReader, decoderContext, builder, field);
        }
    }

    private void decodeCurrentValue(BsonReader reader, DecoderContext decoderContext,
                                    EventTexts.Builder builder, String field) {
        switch (field) {
            case "title":
                builder.title(reader.readString());
                break;
            case "description":
                builder.description(reader.readString());
                break;
            default:
                // This shouldn't happen...
                throw new RuntimeException("Unknown field decoding eventText: " + field);
        }
    }

    @Override
    public void encode(BsonWriter writer, EventTexts value, EncoderContext encoderContext) {
        writer.writeStartDocument();

        encodeCurrentObjectTypeFields(writer, value, encoderContext);

        writer.writeEndDocument();
    }

    private void encodeCurrentObjectTypeFields(BsonWriter writer, EventTexts value, EncoderContext encoderContext) {
        writer.writeString("title", value.getTitle());
        value.getDescription().ifPresent(desc -> writer.writeString("description", desc));
    }

    @Override
    public Class<EventTexts> getEncoderClass() {
        return EventTexts.class;
    }
}
