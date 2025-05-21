package com.diboti.pricecomparatormarket.repo;

import com.diboti.pricecomparatormarket.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountDao extends JpaRepository<Discount, Long> {
}
