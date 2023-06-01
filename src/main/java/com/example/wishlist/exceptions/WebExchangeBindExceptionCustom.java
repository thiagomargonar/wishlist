package com.example.wishlist.exceptions;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WebExchangeBindExceptionCustom implements ExceptionCustomI {

    private static final String MESSAGE = "message";
    private static final String STRING_PATTERN = "[%s] - %s";

    private static final int HTTP_STATUS = 400;
    private static final String STATUS = "status";

    @Override
    public Map<String, Object> getErrorAttributesCustom(Map<String, Object> errorAtributesMap, Throwable throwable) {
        var messageError = ((WebExchangeBindException) throwable)
                .getFieldErrors()
                .stream()
                .map(e -> String.format(STRING_PATTERN, e.getField(), e.getDefaultMessage()))
                .collect(Collectors.joining(", ", "", ""));
        errorAtributesMap.put(MESSAGE, messageError);
        errorAtributesMap.put(STATUS, getStatus(throwable));
        return errorAtributesMap;
    }

    @Override
    public Class<? extends Throwable> exceptionKey() {
        return WebExchangeBindException.class;
    }

    @Override
    public int getStatus(Throwable throwable) {
        return HTTP_STATUS;
    }
}
