package com.javaready.products;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static java.util.UUID.randomUUID;

@WebMvcTest
@ContextConfiguration(classes = RestAssuredConfig.class)
class RestAssuredMockMvcWebContextTests {

    @Test
    void should_reject_product_from_adding_to_shop_when_is_too_old(@Autowired WebApplicationContext context) {
        given()
                .webAppContextSetup(context)
                .body(tooOldMeat())
                .contentType("application/json")
        .when()
                .post("/productCheck")
        .then()
                .statusCode(unauthorized());
    }

    private int unauthorized() {
        return 401;
    }

    private String tooOldMeat() {
        return "{ \"uuid\" : \"7b3e02b3-6b1a-4e75-bdad-cef5b279b074\", \"name\" : \"Szynka podlaska\", \"producerName\" : \"Hortex\", \"dateOfProduction\" : \"2020-01-14\", \"barCode\" : \"128312319018\"}";
    }
}

@Configuration(proxyBeanMethods = false)
class RestAssuredConfig {

    @Bean
    ProductVerifier productVerifier() {
        return new ProductVerifier(Collections.emptySet()) {
            @Override
            ProductVerificationResult verify(Product product) {
                if (product.getDaysFromProduction() < 10) {
                    return ProductVerificationResult.failed(randomUUID());
                }
                return ProductVerificationResult.passed(randomUUID());
            }
        };
    }

    @Bean
    ProductController productController(ProductVerifier productVerifier) {
        return new ProductController(productVerifier);
    }
}

