package com.diboti.pricecomparatormarket.model;

import jakarta.persistence.*;

public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String store;

    @Column(nullable = false)
    private String from_date;

    @Column(nullable = false)
    private Double to_date;

    @Column(nullable = false)
    private double percentage_of_discount;
}
