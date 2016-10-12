package com.github.albertosh.adidas.backend.persistence.codecs;

import com.github.albertosh.adidas.backend.models.EventTexts;
import com.github.albertosh.adidas.backend.models.MultilingualEvent;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MultilingualEventCodec implements Codec<MultilingualEvent> {

    private final LocalDateCodec localDateCodec;
    private final EventTextsCodec eventTextsCodec;

    @Inject
    public MultilingualEventCodec(LocalDateCodec localDateCodec, EventTextsCodec eventTextsCodec) {
        this.localDateCodec = localDateCodec;
        this.eventTextsCodec = eventTextsCodec;
    }

    @Override
    public MultilingualEvent decode(BsonReader reader, DecoderContext decoderContext) {
        MultilingualEvent.Builder builder = new MultilingualEvent.Builder();
        reader.readStartDocument();
        builder.id(reader.readObjectId("_id").toString());

        decodeCurrentObjectTypeFields(reader, decoderContext, builder);

        reader.readEndDocument();
        return builder.build();
    }

    private void decodeCurrentObjectTypeFields(BsonReader bsonReader, DecoderContext decoderContext,
                                               MultilingualEvent.Builder builder) {
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String field = bsonReader.readName();
            decodeCurrentValue(bsonReader, decoderContext, builder, field);
        }
    }

    @SuppressWarnings("deprecation")
    private void decodeCurrentValue(BsonReader reader, DecoderContext decoderContext,
                                    MultilingualEvent.Builder builder, String field) {
        switch (field) {
            case "date":
                builder.date(localDateCodec.decode(reader, decoderContext));
                break;
            case "imageUrl":
                builder.date(localDateCodec.decode(reader, decoderContext));
                break;
            case "texts":
                reader.readStartDocument();
                while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                    String language = reader.readName();
                    EventTexts texts = eventTextsCodec.decode(reader, decoderContext);
                    builder.withText(language, texts);
                }
                reader.readEndDocument();
                break;
            case "defaultLanguage":
                builder.defaultLanguage(reader.readString());
                break;
            default:
                // This shouldn't happen...
                throw new RuntimeException("Unknown field decoding event: " + field);
        }
    }

    @Override
    public void encode(BsonWriter writer, MultilingualEvent value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeObjectId("_id", new ObjectId(value.getId()));

        encodeCurrentObjectTypeFields(writer, value, encoderContext);

        writer.writeEndDocument();
    }

    private void encodeCurrentObjectTypeFields(BsonWriter writer, MultilingualEvent value, EncoderContext encoderContext) {
        localDateCodec.encode(writer, value.getDate(), encoderContext);
        value.getImageUrl().ifPresent(url -> writer.writeString("imageUrl", url));
        writer.writeName("texts");
        writer.writeStartDocument();
        value.getTexts().entrySet().forEach(entry -> {
            writer.writeName(entry.getKey());
            eventTextsCodec.encode(writer, entry.getValue(), encoderContext);
        });
        writer.writeEndDocument();
        writer.writeString("defaultLanguage", value.getDefaultLanguage());
    }

    @Override
    public Class<MultilingualEvent> getEncoderClass() {
        return MultilingualEvent.class;
    }
}
