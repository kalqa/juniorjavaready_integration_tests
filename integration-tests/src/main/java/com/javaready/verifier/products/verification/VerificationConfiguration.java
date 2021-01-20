package com.javaready.verifier.products.verification;

import com.javaready.verifier.products.verification.producername.CompanyHttpVerificationService;
import com.javaready.verifier.products.verification.producername.ProducerNameVerification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class VerificationConfiguration {

    @Bean
    BarCodeVerification barCodeVerification() {
        return new BarCodeVerification();
    }

    @Bean
    CompanyHttpVerificationService companyHttpVerificationService(@Value("${companyXYZ.url:http://example.org}") String url) {
        return new CompanyHttpVerificationService(url);
    }

    @Bean
    ProducerNameVerification producerNameVerification(CompanyHttpVerificationService companyHttpVerificationService) {
        return new ProducerNameVerification(companyHttpVerificationService);
    }
}
