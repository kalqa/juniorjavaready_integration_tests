package com.javaready.verifier.products;

import java.time.LocalDate;
import java.util.UUID;

import com.javaready.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.javaready.verifier.products.ProductVerificationResult.Status.VERIFICATION_FAILED;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = ProductVerifierWithContainersTests.Config.class)
@ActiveProfiles("container")
@Testcontainers
class ProductVerifierWithContainersTests {

    @Container
    private static final PostgreSQLContainer<?> DB_CONTAINER = new PostgreSQLContainer<>("postgres");

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    void should_unsuccessfully_verify_a_product_when_previously_verified(@Autowired VerificationRepository repository, @Autowired ProductVerifier verifier) {
        // given
        Product tooOldMeet = tooOldMeet();
        then(repository.findByProductId(tooOldMeet.getUuid())).isPresent();

        // when
        ProductVerificationResult result = verifier.verify(tooOldMeet);

        // then
        then(result.getProductId()).isEqualTo(tooOldMeet().getUuid());
        then(result.getStatus()).isEqualTo(VERIFICATION_FAILED);
    }

    private Product tooOldMeet() {
        return new Product(UUID.fromString("89c878e3-38f7-4831-af6c-c3b4a0669022"), "kiełbasa", "krakus", LocalDate.now(), "12312333");
    }

    @Configuration(proxyBeanMethods = false)
    @EnableAutoConfiguration
    static class Config extends ProductVerifierConfiguration {

    }
}
