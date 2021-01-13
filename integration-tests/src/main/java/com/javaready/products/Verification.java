package com.javaready.products;

public interface Verification {

    default boolean passes() {
        return false;
    }
    ;
}
