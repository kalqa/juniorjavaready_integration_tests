package com.javaready.products;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.javaready.products.ProductVerificationResult.Status.VERIFICATION_FAILED;
import static org.assertj.core.api.BDDAssertions.then;

@SpringJUnitConfig(Config.class)
class ProductVerificationTest {

    @Test
    void should_pass_verification_when_non_fraud_gets_verified(@Autowired ProductVerifier productVerifier) {
        ProductVerificationResult result = productVerifier.verify(tooOldMeat());

        then(result.getStatus()).isEqualTo(VERIFICATION_FAILED);
    }

    Product tooOldMeat() {
        return new Product(UUID.randomUUID(), "", "", LocalDate.now(), "128312319018");
    }
}