package com.diboti.pricecomparatormarket.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "alerts")
public class ProductAlert extends BaseEntityNumId {
    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private String email;
}
