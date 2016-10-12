package com.github.albertosh.adidas.backend.persistence.codecs;

import com.github.albertosh.adidas.backend.models.user.AuthInfo;
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
public class AuthInfoCodec
        extends ObjectWithIdCodec<AuthInfo>
        implements Codec<AuthInfo> {

    @Inject
    public AuthInfoCodec() {

    }

    @Override
    @SuppressWarnings("unchecked")
    protected <B extends ObjectWithId.Builder<AuthInfo>> B getBuilder() {
        return (B) new AuthInfo.Builder();
    }

    @Override
    public Class<AuthInfo> getEncoderClass() {
        return AuthInfo.class;
    }

    @Override
    protected <B extends ObjectWithId.Builder<AuthInfo>> void decodeCurrentValue(BsonReader reader, DecoderContext decoderContext, B builder, String field) {
        AuthInfo.Builder authInfoBuilder = (AuthInfo.Builder) builder;
        switch (field) {
            case "uuid":
                authInfoBuilder.uuid(reader.readString());
                break;
            case "token":
                authInfoBuilder.token(reader.readString());
                break;
            default:
                // This shouldn't happen...
                throw new RuntimeException("Unknown field decoding event: " + field);
        }
    }

    @Override
    protected void encodeCurrentObjectTypeFields(BsonWriter writer, AuthInfo value, EncoderContext context) {
        writer.writeString("uuid", value.getUuid());
        writer.writeString("token", value.getToken());
    }
}
