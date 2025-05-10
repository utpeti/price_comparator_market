package com.diboti.pricecomparatormarket.repo;

import com.diboti.pricecomparatormarket.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
}
