package com.github.albertosh.adidas.backend.controllers;

import com.github.albertosh.adidas.backend.models.event.Event;
import com.github.albertosh.adidas.backend.usecases.enrolls.enrollevent.EnrollEventUseCaseError;
import com.github.albertosh.adidas.backend.usecases.enrolls.enrollevent.EnrollEventUseCaseInput;
import com.github.albertosh.adidas.backend.usecases.enrolls.enrollevent.IEnrollEventUseCase;
import com.github.albertosh.swagplash.annotations.ApiOperation;
import com.github.albertosh.swagplash.annotations.ApiPathParam;
import com.github.albertosh.swagplash.annotations.ApiResponse;
import com.github.albertosh.swagplash.annotations.SecureEndPoint;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.mvc.Result;
import rx.Single;

import static com.github.albertosh.adidas.backend.utils.ObservableAndFutures.fromSingle;
import static com.github.albertosh.swagplash.annotations.ApiOperation.HttpMethod.POST;
import static play.libs.Json.toJson;

@Singleton
@Api("Enrolls")
public class EnrollsController extends LoggedInController {

    private final IEnrollEventUseCase enrollEventUseCase;

    @Inject
    public EnrollsController(IEnrollEventUseCase enrollEventUseCase) {
        this.enrollEventUseCase = enrollEventUseCase;
    }

    @ApiOperation(httpMethod = POST, path = "/event/{id}/enroll")
    @SecureEndPoint(value = "token")
    @ApiResponse(code = OK, message = "Enroll done", response = Event.class)
    @ApiResponse(code = NOT_FOUND, message = "Event not found")
    public CompletionStage<Result> enroll(
            @ApiPathParam String eventId
    ) {
        EnrollEventUseCaseInput input = new EnrollEventUseCaseInput.Builder()
                .eventId(eventId)
                .uuid(getCurrentUUID())
                .build();

        return fromSingle(
                enrollEventUseCase.execute(input)
                        .map(event -> ok(toJson(event)))
                        .onErrorResumeNext(error -> {
                            if (error == EnrollEventUseCaseError.eventDoesNotExist) {
                                return Single.just(badRequest(toJson(error)));
                            } else
                                return Single.error(error);
                        })
        );
    }
}
