package com.javaready.products;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;

import static com.javaready.products.ProductVerificationResult.Status.VERIFICATION_FAILED;

class NoNeedToStartSpringContextIocTests {

    @Test
    void without_context_testing_example() {
        ProductVerifier productVerifier = new TestConfig().testProductVerifier();

        ProductVerificationResult result = productVerifier.verify(tooOldMeat());

        BDDAssertions.then(result.getStatus()).isEqualTo(VERIFICATION_FAILED);
    }

    Product tooOldMeat() {
        return new Product(UUID.randomUUID(), "", "Krakus", LocalDate.now(), "128312319018");
    }
}

class TestConfig extends Config {

    Verification testIdVerification() {
        return barcodeVerification(new InMemoryDatabaseAccessor());
    }

    ProductVerifier testProductVerifier() {
        return productVerifier(Collections.singleton(testIdVerification()));
    }
}
