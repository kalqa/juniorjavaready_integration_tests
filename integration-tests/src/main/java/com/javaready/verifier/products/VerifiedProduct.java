package com.javaready.verifier.products;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "verified")
@Getter
@Setter
class VerifiedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private UUID productId;
    @NotBlank
    private String status;

    VerifiedProduct(UUID productId, ProductVerificationResult.Status status) {
        this.productId = productId;
        this.status = status.toString();
    }

    protected VerifiedProduct() {
    }
}