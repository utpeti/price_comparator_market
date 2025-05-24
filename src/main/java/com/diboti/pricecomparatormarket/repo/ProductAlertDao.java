package com.diboti.pricecomparatormarket.repo;

import com.diboti.pricecomparatormarket.model.ProductAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAlertDao extends JpaRepository<ProductAlert, Long> {
    Object findByProductIdAndEmail(String productId, String email);
}
