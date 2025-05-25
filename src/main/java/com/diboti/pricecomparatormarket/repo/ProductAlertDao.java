package com.diboti.pricecomparatormarket.repo;

import com.diboti.pricecomparatormarket.model.ProductAlert;
import com.diboti.pricecomparatormarket.repo.exceptions.InvalidDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAlertDao extends JpaRepository<ProductAlert, Long> {
    Object findByProductIdAndEmail(String productId, String email) throws InvalidDataAccessException;
}
