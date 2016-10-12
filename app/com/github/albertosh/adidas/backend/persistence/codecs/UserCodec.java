package com.github.albertosh.adidas.backend.persistence.codecs;

import com.github.albertosh.adidas.backend.models.user.User;
import com.github.albertosh.adidas.backend.persistence.core.ObjectWithId;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserCodec
        extends ObjectWithIdCodec<User>
        implements Codec<User> {

    private final LocalDateCodec localDateCodec;

    @Inject
    public UserCodec(LocalDateCodec localDateCodec) {
        this.localDateCodec = localDateCodec;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends ObjectWithId.Builder<User>> B getBuilder() {
        return (B) new User.Builder();
    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }

    @Override
    protected <B extends ObjectWithId.Builder<User>> void decodeCurrentValue(BsonReader reader, DecoderContext decoderContext, B builder, String field) {
        User.Builder userBuilder = (User.Builder) builder;
        switch (field) {
            case "email":
                userBuilder.email(reader.readString());
                break;
            case "firstName":
                userBuilder.firstName(reader.readString());
                break;
            case "lastName":
                userBuilder.lastName(reader.readString());
                break;
            case "country":
                userBuilder.country(reader.readString());
                break;
            case "language":
                userBuilder.preferredLanguage(reader.readString());
                break;
            case "dateOfBirth":
                userBuilder.dateOfBirth(localDateCodec.decode(reader, decoderContext));
                break;
            default:
                // This shouldn't happen...
                throw new RuntimeException("Unknown field decoding event: " + field);
        }
    }

    @Override
    protected void encodeCurrentObjectTypeFields(BsonWriter writer, User value, EncoderContext context) {
        writer.writeString("email", value.getEmail());
        writer.writeString("firstName", value.getFirstName());
        writer.writeString("lastName", value.getLastName());
        writer.writeString("country", value.getCountry());
        writer.writeString("language", value.getPreferredLanguage());
        writer.writeName("dateOfBirth");
        localDateCodec.encode(writer, value.getDateOfBirth(), context);
    }
}
