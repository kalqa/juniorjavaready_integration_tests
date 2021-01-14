package com.javaready.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductVerifier productVerifier;

    ProductController(ProductVerifier productVerifier) {
        this.productVerifier = productVerifier;
    }

    @PostMapping(value = "/productCheck", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> productCheck(@RequestBody Product product) {
        log.info("Received a verification request for product [{}]", product);
        ProductVerificationResult result = productVerifier.verify(product);
        if (result.getStatus() == ProductVerificationResult.Status.VERIFICATION_FAILED) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }
}
