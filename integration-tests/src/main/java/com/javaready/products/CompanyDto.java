package com.javaready.products;

import java.util.UUID;

class CompanyDto {

    private final UUID uuid;
    private final Product product;

    public CompanyDto(UUID uuid, Product product) {
        this.uuid = uuid;
        this.product = product;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Product getProduct() {
        return product;
    }

    public String getCompanyName() {
        return product.getProducerName();
    }
}
