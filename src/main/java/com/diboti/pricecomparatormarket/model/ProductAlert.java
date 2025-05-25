package com.diboti.pricecomparatormarket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "alerts")
public class ProductAlert extends BaseEntityNumId {
    @Column(name= "product_id", nullable = false)
    private String productId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Double price;
}
