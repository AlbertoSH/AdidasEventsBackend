package com.github.albertosh.adidas.backend.utils;

import com.github.albertosh.swagplash.annotations.ApiModel;
import com.github.albertosh.swagplash.annotations.ApiModelProperty;

@ApiModel
/**
 * Used just for documentation purposes
 */
public class ErrorResult {

    @ApiModelProperty
    private int code;
    @ApiModelProperty
    private String text;

    private ErrorResult() {}
}
