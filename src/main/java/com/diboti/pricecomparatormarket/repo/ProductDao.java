package com.diboti.pricecomparatormarket.repo;

import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.repo.exceptions.InvalidDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    Optional<Product> findById(String productId);

    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Collection<Product> findAllByName(String name);
}
