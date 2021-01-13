package com.javaready.products;

import java.util.UUID;

public class ProductVerificationResult {

    private UUID productId;
    private Status status;

    private ProductVerificationResult(UUID productId, Status status) {
        this.productId = productId;
        this.status = status;
    }

    public ProductVerificationResult() {
    }

    public static ProductVerificationResult passed(UUID productId) {
        return new ProductVerificationResult(productId, Status.VERIFICATION_PASSED);
    }

    public static ProductVerificationResult failed(UUID productId) {
        return new ProductVerificationResult(productId, Status.VERIFICATION_FAILED);
    }

    public UUID getProductId() {
        return productId;
    }

    public Status getStatus() {
        return status;
    }

    enum Status {
        VERIFICATION_PASSED,
        VERIFICATION_FAILED
    }
}
