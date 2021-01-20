package com.javaready.verifier.products.verification.producername;

import java.util.UUID;

import com.javaready.product.CompanyDto;
import com.javaready.product.Product;
import com.javaready.verifier.Verification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProducerNameVerification implements Verification {

    CompanyHttpVerificationService companyHttpVerificationService;

    @Override
    public boolean passes(Product product) {
        return companyHttpVerificationService.verify(new CompanyDto(UUID.randomUUID(), product)).passed();
    }
}
