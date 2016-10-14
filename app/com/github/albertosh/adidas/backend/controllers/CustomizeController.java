package com.github.albertosh.adidas.backend.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.albertosh.swagplash.annotations.Api;
import com.github.albertosh.swagplash.annotations.ApiOperation;

import javax.inject.Singleton;

import play.libs.Json;
import play.mvc.Result;

import static play.mvc.Results.ok;

@Singleton
@Api("Custom")
public class CustomizeController {

    @ApiOperation(httpMethod = ApiOperation.HttpMethod.GET, path = "custom")
    public Result getCustomProperties() {

        ObjectNode txtTitle = Json.newObject();
        ObjectNode callParameters = Json.newObject();
        callParameters.set("parameterTypes", Json.newArray()
                .add(float.class.getName()));
        callParameters
                .set("values", Json.newArray()
                        .add("30f"));
        txtTitle
                .set("setTextSize", callParameters);



        return ok(Json.newObject()
                .put("defaultImageUrl", "https://www.fineprintnyc.com/images/blog/history-adidas-logo/adidas-equipment-logo.jpg")
                .set("txtTitle", txtTitle));
    }
}
