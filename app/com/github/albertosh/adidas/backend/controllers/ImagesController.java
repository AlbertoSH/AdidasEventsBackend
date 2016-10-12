package com.github.albertosh.adidas.backend.controllers;

import com.github.albertosh.swagplash.annotations.Api;
import com.github.albertosh.swagplash.annotations.ApiOperation;
import com.github.albertosh.swagplash.annotations.ApiPathParam;
import com.github.albertosh.swagplash.annotations.ApiResponse;

import java.io.File;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import play.mvc.Controller;
import play.mvc.Result;

import static com.github.albertosh.swagplash.annotations.ApiOperation.HttpMethod.GET;

@Api
@Singleton
public class ImagesController extends Controller {

    private final File basePath;

    @Inject
    public ImagesController(@Named("storageBasePath") File basePath) {
        this.basePath = basePath;
    }

    @ApiOperation(httpMethod = GET, path = "/event")
    @ApiResponse(code = OK, message = "Image recovered")
    @ApiResponse(code = NOT_FOUND, message = "Image not found")
    public Result get(@ApiPathParam String id) {
        File folder = new File(basePath, "images");
        File file = new File(folder, id + ".jpg");
        if (file.exists())
            return ok(file);
        else
            return notFound();
    }
}
