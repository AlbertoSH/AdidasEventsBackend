package com.github.albertosh.adidas.backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.albertosh.swagplash.SwagplashMapper;
import com.github.albertosh.swagplash.SwagplashProvider;
import com.github.albertosh.swagplash.annotations.Contact;
import com.github.albertosh.swagplash.annotations.Info;
import com.github.albertosh.swagplash.annotations.SwaggerDefinition;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import play.Environment;
import play.mvc.Controller;
import play.mvc.Result;

@SwaggerDefinition(
        info = @Info(
                title = "Adidas events",
                version = "1.0.0",
                contact = @Contact(
                        name = "Alberto Sanz",
                        email = "alberto.sanz.herrero@gmail.com"
                )
        ),
        host = "localhost:9000",
        schemes = SwaggerDefinition.Scheme.HTTP,
        consumes = {"application/json", "application/vnd.api+json", "multipart/form-data"},
        produces = {"application/json"}
)

public class Swagplash extends Controller {

    private final SwagplashProvider provider;
    private final SwagplashMapper mapper;
    private final Environment environment;

    @Inject
    public Swagplash(SwagplashProvider provider, SwagplashMapper mapper, Environment environment) {
        this.provider = provider;
        this.mapper = mapper;
        this.environment = environment;
    }

    public CompletionStage<Result> getDoc(boolean pretty) {
        if (environment.isDev()) {
            return provider.get()
                    .thenApply(swagger -> {
                        try {
                            if (pretty)
                                return ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(swagger));
                            else
                                return ok(mapper.writeValueAsString(swagger));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return internalServerError("Something failed while writing swagger.json");
                        }
                    });
        } else {
            return CompletableFuture.completedFuture(forbidden());
        }
    }

    public Result serveCss(String fileName) {
        if (environment.isDev()) {
            File file = new File("public/html/css/" + fileName);

            if (file.exists())
                return ok(file);
        }
        return forbidden();
    }

    public Result serveLib(String fileName) {
        if (environment.isDev()) {
            File file = new File("public/html/lib/" + fileName);

            if (file.exists())
                return ok(file);
        }
        return forbidden();
    }

    public Result serveSwagger() {
        if (environment.isDev()) {
            File file = new File("public/html/index.html");

            if (file.exists())
                return ok(file).as("text/html");
        }
        return forbidden();
    }


    public Result serveSwaggerJs() {
        if (environment.isDev()) {
            File file = new File("public/html/swagger-ui.min.js");

            if (file.exists())
                return ok(file);
        }

        return forbidden();
    }

}
