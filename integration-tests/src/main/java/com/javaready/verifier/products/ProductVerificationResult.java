package com.javaready.verifier.products;

import java.util.UUID;

public class ProductVerificationResult {

    private UUID productId;
    private Status status;

    public ProductVerificationResult(UUID productId, Status status) {
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

    public boolean passed() {
        return Status.VERIFICATION_PASSED.equals(status);
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        VERIFICATION_PASSED,
        VERIFICATION_FAILED

    }

}
