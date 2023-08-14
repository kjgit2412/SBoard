package org.koreadit.validators;

public interface NullBlankCheck {

    // null이거나 blank면 Exception
    default void checkNullBlank(String str, RuntimeException e) {
        if(str==null || str.isBlank()) {
            throw e;
        }
    }
}
