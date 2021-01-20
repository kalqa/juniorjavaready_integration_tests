package com.javaready.verifier.products;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

interface VerificationRepository extends CrudRepository<VerifiedProduct, Long> {

    Optional<VerifiedProduct> findByProductId(UUID number);
}