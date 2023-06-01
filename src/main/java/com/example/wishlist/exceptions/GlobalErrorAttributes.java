package com.example.wishlist.exceptions;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(-1)
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    private final Map<Object, Object> customExceptions;
    private final ExceptionGenericCustom exceptionGenericCustom;

    public GlobalErrorAttributes(List<ExceptionCustomI> customExceptionsInstance, ExceptionGenericCustom exceptionGenericCustom) {
        this.exceptionGenericCustom = exceptionGenericCustom;
        this.customExceptions = new IdentityHashMap<>();
        customExceptionsInstance.forEach(x -> this.customExceptions.put(x.exceptionKey(), x));
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        var errorAtributesMap = super.getErrorAttributes(request, options);
        errorAtributesMap.remove("requestId");
        errorAtributesMap.remove("path");
        var throwable = getError(request);

        var exceptionsOrDefault = customExceptions.getOrDefault(throwable.getClass(), exceptionGenericCustom);

        return getExceptionInStrategy((ExceptionCustomI) exceptionsOrDefault, errorAtributesMap, throwable);
    }

    private Map<String, Object> getExceptionInStrategy(ExceptionCustomI exceptionCustom, Map<String, Object> errorAtributesMap, Throwable throwable) {
        return exceptionCustom.getErrorAttributesCustom(errorAtributesMap, throwable);
    }
}
