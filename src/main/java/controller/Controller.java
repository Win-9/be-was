package controller;

import dto.ResourceDto;

import java.util.function.BiFunction;

public interface Controller {
    BiFunction<String, Object, ResourceDto> getCorrectMethod(String path);
}
