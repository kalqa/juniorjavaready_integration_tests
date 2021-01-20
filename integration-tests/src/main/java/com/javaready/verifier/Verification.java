package com.javaready.verifier;

import com.javaready.product.Product;

public interface Verification {

    default boolean passes(Product product) {
        return false;
    }
}
