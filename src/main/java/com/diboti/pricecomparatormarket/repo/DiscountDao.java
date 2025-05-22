package com.diboti.pricecomparatormarket.repo;

import com.diboti.pricecomparatormarket.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DiscountDao extends JpaRepository<Discount, Long> {
    @Query("SELECT d FROM Discount d WHERE d.product.id = ?1")
    Collection<Discount> findAllByProductId(String productId);
}
