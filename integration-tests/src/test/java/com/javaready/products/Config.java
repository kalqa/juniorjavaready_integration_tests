package com.javaready.products;

import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class Config {

    @Bean
    ProductVerifier productVerifier(Set<Verification> verifications) {
        return new ProductVerifier(verifications);
    }

    @Bean
    HttpCallMaker httpCallMaker() {
        return new HttpCallMaker();
    }

    @Bean
    DatabaseAccessor accessor() {
        return new DatabaseAccessor();
    }

    @Bean
    Verification dateVerification(HttpCallMaker httpCallMaker, DatabaseAccessor accessor) {
        return new DateVerification(httpCallMaker, accessor);
    }

    @Bean
    Verification idVerification(DatabaseAccessor accessor) {
        return new BarCodeVerification(accessor);
    }

    @Bean
    EventEmitter eventEmitter() {
        return new EventEmitter();
    }

    @Bean
    Verification nameVerification(EventEmitter eventEmitter) {
        return new ProducerNameVerification(eventEmitter);
    }
}
