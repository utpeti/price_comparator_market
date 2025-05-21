package com.diboti.pricecomparatormarket.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "discounts")
public class Discount extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String store;

    @Column(nullable = false)
    private String from_date;

    @Column(nullable = false)
    private String to_date;

    @Column(nullable = false)
    private double percentage_of_discount;
}
