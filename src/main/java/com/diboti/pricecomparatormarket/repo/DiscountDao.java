package com.diboti.pricecomparatormarket.repo;

import com.diboti.pricecomparatormarket.model.Discount;
import com.diboti.pricecomparatormarket.repo.exceptions.InvalidDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DiscountDao extends JpaRepository<Discount, Long> {
    @Query("SELECT d FROM Discount d WHERE d.product.id = ?1")
    Collection<Discount> findAllByProductId(String productId) throws InvalidDataAccessException;

    @Query("SELECT d FROM Discount d WHERE d.product.id = ?1 AND d.store = ?2")
    Collection<Discount> findAllByProductIdAndStore(String productId, String store) throws InvalidDataAccessException;

    @Query("SELECT d FROM Discount d WHERE d.product.id = ?1 AND d.product.category = ?2")
    Collection<Discount> findAllByProductIdAndProductCategory(String productId, String productCategory)
            throws InvalidDataAccessException;

    @Query("SELECT d FROM Discount d WHERE d.product.id = ?1 AND d.product.brand = ?2")
    Collection<Discount> findAllByProductIdAndBrand(String productId, String brand) throws InvalidDataAccessException;
}
