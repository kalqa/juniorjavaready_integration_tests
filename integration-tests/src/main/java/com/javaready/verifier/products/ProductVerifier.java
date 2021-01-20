package com.javaready.verifier.products;

import java.util.Set;

import com.javaready.product.Product;
import com.javaready.verifier.Verification;

public class ProductVerifier {

    private final Set<Verification> verifications;
    private final VerificationRepository repository;

    public ProductVerifier(Set<Verification> verifications, VerificationRepository repository) {
        this.verifications = verifications;
        this.repository = repository;
    }

    ProductVerificationResult verify(Product product) {
        return repository.findByProductId(product.getUuid())
                .map(this::toResult)
                .orElseGet(() -> verifyProduct(product));
    }

    private ProductVerificationResult toResult(VerifiedProduct product) {
        return new ProductVerificationResult(product.getProductId(), ProductVerificationResult.Status.valueOf(product.getStatus()));
    }

    private ProductVerificationResult verifyProduct(Product product) {
        if (doesAllVerificationPassed(product)) {
            return ProductVerificationResult.passed(product.getUuid());
        }
        return ProductVerificationResult.failed(product.getUuid());
    }

    private boolean doesAllVerificationPassed(Product product) {
        return verifications.stream().allMatch(verification -> verification
                .passes(product));
    }
}