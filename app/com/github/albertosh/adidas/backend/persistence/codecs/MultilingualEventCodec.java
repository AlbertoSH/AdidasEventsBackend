package com.github.albertosh.adidas.backend.persistence.codecs;

import com.github.albertosh.adidas.backend.models.event.EventTexts;
import com.github.albertosh.adidas.backend.models.event.MultilingualEvent;
import com.github.albertosh.adidas.backend.persistence.core.ObjectWithId;

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
public class MultilingualEventCodec
        extends ObjectWithIdCodec<MultilingualEvent>
        implements Codec<MultilingualEvent> {

    private final LocalDateCodec localDateCodec;
    private final EventTextsCodec eventTextsCodec;

    @Inject
    public MultilingualEventCodec(LocalDateCodec localDateCodec, EventTextsCodec eventTextsCodec) {
        this.localDateCodec = localDateCodec;
        this.eventTextsCodec = eventTextsCodec;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends ObjectWithId.Builder<MultilingualEvent>> B getBuilder() {
        return (B) new MultilingualEvent.Builder();
    }

    @Override
    protected <B extends ObjectWithId.Builder<MultilingualEvent>> void decodeCurrentValue(BsonReader reader, DecoderContext decoderContext, B builder, String field) {
        MultilingualEvent.Builder eventBuilder = (MultilingualEvent.Builder) builder;
        switch (field) {
            case "date":
                eventBuilder.date(localDateCodec.decode(reader, decoderContext));
                break;
            case "imageUrl":
                eventBuilder.imageUrl(reader.readString());
                break;
            case "imageId":
                eventBuilder.imageId(reader.readObjectId().toString());
                break;
            case "texts":
                reader.readStartDocument();
                while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                    String language = reader.readName();
                    EventTexts texts = eventTextsCodec.decode(reader, decoderContext);
                    eventBuilder.withText(language, texts);
                }
                reader.readEndDocument();
                break;
            case "defaultLanguage":
                eventBuilder.defaultLanguage(reader.readString());
                break;
            default:
                // This shouldn't happen...
                throw new RuntimeException("Unknown field decoding event: " + field);
        }
    }

    @Override
    protected void encodeCurrentObjectTypeFields(BsonWriter writer, MultilingualEvent value, EncoderContext context) {
        writer.writeName("date");
        localDateCodec.encode(writer, value.getDate(), context);
        value.getImageUrl()
                .ifPresent(url -> writer.writeString("imageUrl", url));
        value.getImageId()
                .map(ObjectId::new)
                .ifPresent(id -> writer.writeObjectId("imageId", id));
        writer.writeName("texts");
        writer.writeStartDocument();
        value.getTexts().entrySet().forEach(entry -> {
            writer.writeName(entry.getKey());
            eventTextsCodec.encode(writer, entry.getValue(), context);
        });
        writer.writeEndDocument();
        writer.writeString("defaultLanguage", value.getDefaultLanguage());
    }

    @Override
    public Class<MultilingualEvent> getEncoderClass() {
        return MultilingualEvent.class;
    }
}
