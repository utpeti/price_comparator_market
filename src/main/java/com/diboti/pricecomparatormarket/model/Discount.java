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

    @Column(name = "from_date", nullable = false)
    private String fromDate;

    @Column(name = "to_date", nullable = false)
    private String toDate;

    @Column(name = "percentage_of_discount", nullable = false)
    private double percentageOfDiscount;

    @Column(name = "added_on", nullable = false)
    private String addedOn;
}
