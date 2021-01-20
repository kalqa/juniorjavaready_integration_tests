package com.javaready.product;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import lombok.Getter;

@Getter
public class Product {

    private final UUID uuid;
    private final String name;
    private final String producerName;
    private final LocalDate dateOfProduction;
    private final String barCode;

    Product(UUID uuid, String name, String producerName, LocalDate dateOfProduction, String barCode) {
        this.uuid = uuid;
        this.name = name;
        this.producerName = producerName;
        this.dateOfProduction = dateOfProduction;
        this.barCode = barCode;
    }

    int getDaysFromProduction() {
        LocalDate currentDate = LocalDate.now();
        if (dateOfProduction != null) {
            return Period.between(dateOfProduction, currentDate).getDays();
        } else {
            throw new IllegalStateException("Date of production cannot be null");
        }
    }
}