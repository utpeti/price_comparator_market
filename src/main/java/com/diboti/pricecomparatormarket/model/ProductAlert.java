package com.diboti.pricecomparatormarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alerts")
public class ProductAlert extends BaseEntityNumId {
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Double price;
}
