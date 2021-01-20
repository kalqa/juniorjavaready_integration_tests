package com.javaready.verifier.products;

import java.util.Collections;
import java.util.Set;

import com.javaready.verifier.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class ProductVerifierConfiguration {

    @Bean
    ProductVerifier customerVerifier(@Autowired(required = false) Set<Verification> verifications, VerificationRepository verificationRepository) {
        return new ProductVerifier(verifications != null ? verifications : Collections.emptySet(), verificationRepository);
    }
}
