package com.github.albertosh.adidas.backend.controllers;

import com.github.albertosh.adidas.backend.models.Event;
import com.github.albertosh.adidas.backend.usecases.event.getEvents.GetEventsUseCaseInput;
import com.github.albertosh.adidas.backend.usecases.event.getEvents.IGetEventsUseCase;
import com.github.albertosh.swagplash.annotations.Api;
import com.github.albertosh.swagplash.annotations.ApiOperation;
import com.github.albertosh.swagplash.annotations.ApiQueryParam;
import com.github.albertosh.swagplash.annotations.ApiResponse;

import java.util.concurrent.CompletionStage;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import play.mvc.Controller;
import play.mvc.Result;

import static com.github.albertosh.adidas.backend.utils.ObservableAndFutures.fromSingle;
import static com.github.albertosh.swagplash.annotations.ApiOperation.HttpMethod.GET;
import static play.libs.Json.toJson;

@Singleton
@Api("Events")
public class EventsController extends Controller {

    private final IGetEventsUseCase getEventsUseCase;

    @Inject
    public EventsController(IGetEventsUseCase getEventsUseCase) {
        this.getEventsUseCase = getEventsUseCase;
    }

    @ApiOperation(httpMethod = GET, path = "/event")
    @ApiResponse(code = OK, message = "Events recovered", response = Event.class, responseContainer = "array")
    public CompletionStage<Result> getAll(
            @ApiQueryParam @Nullable Integer page,
            @ApiQueryParam @Nullable Integer pageSize) {
        GetEventsUseCaseInput input = new GetEventsUseCaseInput.Builder()
                .page(page)
                .pageSize(pageSize)
                .build();

        return fromSingle(getEventsUseCase.execute(input)
                .map(events -> ok(toJson(events))));

    }

}
