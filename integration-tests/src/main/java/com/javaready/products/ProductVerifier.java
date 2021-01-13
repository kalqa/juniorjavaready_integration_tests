package com.javaready.products;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ProductVerifier {

    private final Set<Verification> verifications;

    public ProductVerifier(Verification... verifications) {
        this.verifications = new HashSet<>(Arrays.asList(verifications));
    }

    public ProductVerifier(Set<Verification> verfications) {
        this.verifications = verfications;
    }

    ProductVerificationResult verify(Product product) {
        if (verifications.stream().allMatch(Verification::passes)) {
            return ProductVerificationResult.passed(product.getUuid());
        }
        return ProductVerificationResult.failed(product.getUuid());
    }
}

class DateVerification implements Verification {

    private final HttpCallMaker maker;
    private final DatabaseAccessor accessor;

    DateVerification(HttpCallMaker maker, DatabaseAccessor accessor) {
        this.maker = maker;
        this.accessor = accessor;
    }
}

class BarCodeVerification implements Verification {

    private final DatabaseAccessor accessor;

    BarCodeVerification(DatabaseAccessor accessor) {
        this.accessor = accessor;
    }
}

class ProducerNameVerification implements Verification {

    private final EventEmitter eventEmitter;

    ProducerNameVerification(EventEmitter eventEmitter) {
        this.eventEmitter = eventEmitter;
    }
}

class HttpCallMaker {

}

class DatabaseAccessor {

}

class EventEmitter {

}