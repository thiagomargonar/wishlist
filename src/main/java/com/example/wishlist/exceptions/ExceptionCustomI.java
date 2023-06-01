package com.example.wishlist.exceptions;

import java.util.Map;

public interface ExceptionCustomI {
    Map<String, Object> getErrorAttributesCustom(Map<String, Object> objectMap, Throwable throwable);

    Class<? extends Throwable> exceptionKey();

    int getStatus(Throwable throwable);
}
