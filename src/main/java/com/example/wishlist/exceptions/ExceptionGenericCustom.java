package com.example.wishlist.exceptions;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExceptionGenericCustom implements ExceptionCustomI {

    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    private static final int HTTP_STATUS = 500;

    @Override
    public Map<String, Object> getErrorAttributesCustom(Map<String, Object> errorAtributesMap, Throwable throwable) {
        errorAtributesMap.put(MESSAGE, throwable.getMessage());
        errorAtributesMap.put(STATUS, getStatus(throwable));
        return errorAtributesMap;
    }

    @Override
    public Class<? extends Throwable> exceptionKey() {
        return null;
    }

    @Override
    public int getStatus(Throwable throwable) {
        return HTTP_STATUS;
    }
}
