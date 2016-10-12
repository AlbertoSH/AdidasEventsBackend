package com.github.albertosh.adidas.backend.controllers;

import com.github.albertosh.adidas.backend.models.event.Event;
import com.github.albertosh.adidas.backend.security.AdminAuthorizationCheck;
import com.github.albertosh.adidas.backend.usecases.event.createEvents.CreateEventUseCaseInput;
import com.github.albertosh.adidas.backend.usecases.event.createEvents.ICreateEventsUseCase;
import com.github.albertosh.adidas.backend.usecases.event.getevents.GetEventsUseCaseInput;
import com.github.albertosh.adidas.backend.usecases.event.getevents.IGetEventsUseCase;
import com.github.albertosh.adidas.backend.usecases.event.getsingleevent.GetSingleEventUseCaseInput;
import com.github.albertosh.adidas.backend.usecases.event.getsingleevent.IGetSingleEventUseCase;
import com.github.albertosh.swagplash.annotations.Api;
import com.github.albertosh.swagplash.annotations.ApiBodyParam;
import com.github.albertosh.swagplash.annotations.ApiOperation;
import com.github.albertosh.swagplash.annotations.ApiPathParam;
import com.github.albertosh.swagplash.annotations.ApiQueryParam;
import com.github.albertosh.swagplash.annotations.ApiResponse;
import com.github.albertosh.swagplash.annotations.SecureEndPoint;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import play.api.libs.Files;
import play.mvc.Controller;
import play.mvc.Result;

import static com.github.albertosh.adidas.backend.utils.ObservableAndFutures.fromSingle;
import static com.github.albertosh.swagplash.annotations.ApiOperation.HttpMethod.GET;
import static com.github.albertosh.swagplash.annotations.ApiOperation.HttpMethod.POST;
import static play.libs.Json.toJson;

@Singleton
@Api("Events")
public class EventsController extends Controller {

    private final IGetEventsUseCase getEventsUseCase;
    private final IGetSingleEventUseCase getSingleEventUseCase;
    private final ICreateEventsUseCase createEventsUseCase;

    @Inject
    public EventsController(IGetEventsUseCase getEventsUseCase, IGetSingleEventUseCase getSingleEventUseCase, ICreateEventsUseCase createEventsUseCase) {
        this.getEventsUseCase = getEventsUseCase;
        this.getSingleEventUseCase = getSingleEventUseCase;
        this.createEventsUseCase = createEventsUseCase;
    }

    @ApiOperation(httpMethod = GET, path = "/event")
    @ApiResponse(code = OK, message = "Events recovered", response = Event.class, responseContainer = "array")
    public CompletionStage<Result> getAll(
            @ApiQueryParam @Nullable Integer page,
            @ApiQueryParam @Nullable Integer pageSize,
            @ApiQueryParam @Nullable String language) {
        GetEventsUseCaseInput input = new GetEventsUseCaseInput.Builder()
                .page(page)
                .pageSize(pageSize)
                .language(language)
                .build();

        return fromSingle(
                getEventsUseCase.execute(input)
                        .map(events -> ok(toJson(events)))
        );

    }

    @ApiOperation(httpMethod = GET, path = "/event/{id}")
    @ApiResponse(code = OK, message = "Event recovered", response = Event.class)
    @ApiResponse(code = NOT_FOUND, message = "Event not found", response = Event.class)
    public CompletionStage<Result> getSingle(
            @ApiPathParam String id,
            @ApiQueryParam @Nullable String language) {
        GetSingleEventUseCaseInput input = new GetSingleEventUseCaseInput.Builder()
                .id(id)
                .language(language)
                .build();

        return fromSingle(
                getSingleEventUseCase.execute(input)
                        .map(event -> ok(toJson(event)))
                        .onErrorReturn(error -> notFound())
        );
    }

    @ApiOperation(httpMethod = POST, path = "/event")
    @SecureEndPoint(value = "admin", alternateChecker = AdminAuthorizationCheck.class)
    @ApiBodyParam(name = "defaultLanguage", required = true)
    @ApiBodyParam(name = "title", required = true)
    @ApiBodyParam(name = "description")
    @ApiBodyParam(name = "date", dataType = ApiBodyParam.DataType.DATE, required = true)
    @ApiBodyParam(name = "image", dataType = ApiBodyParam.DataType.FILE)
    @ApiBodyParam(name = "imageUrl")
    @ApiResponse(code = CREATED, message = "Event created", response = Event.class)
    public CompletionStage<Result> create() {
        @SuppressWarnings("unchecked")
        CreateEventUseCaseInput input = new CreateEventUseCaseInput.Builder()
                .defaultLanguage((String) ctx().args.get("defaultLanguage"))
                .title((String) ctx().args.get("title"))
                .description(((Optional<String>) ctx().args.get("description")).orElse(null))
                .date((LocalDate) ctx().args.get("date"))
                .image(((Optional<Files.TemporaryFile>) ctx().args.get("image")).map(Files.TemporaryFile::file).orElse(null))
                .imageUrl(((Optional<String>) ctx().args.get("imageUrl")).orElse(null))
                .build();

        return fromSingle(
                createEventsUseCase.execute(input)
                        .map(event -> created(toJson(event)))
        );
    }
}
