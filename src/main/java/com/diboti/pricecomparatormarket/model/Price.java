package com.diboti.pricecomparatormarket.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String store;

    @Column(nullable = false)
    private String date;

    @Column(name = "price", nullable = false)
    private Double value;
}