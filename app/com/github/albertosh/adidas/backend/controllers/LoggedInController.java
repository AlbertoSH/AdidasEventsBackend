package com.github.albertosh.adidas.backend.controllers;

import play.mvc.Controller;

public abstract class LoggedInController extends Controller {

    protected String getCurrentUUID() {
        String uuid = (String) ctx().args.get("currentUUID");
        if (uuid == null)
            throw new IllegalStateException("This method should only be used in a @SecureEndPoint annotated method");
        return uuid;
    }

}
