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

    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Collection<Product> findAllByName(String productName) throws InvalidDataAccessException;

    @Query("SELECT p FROM Product p WHERE p.id = ?1")
    Collection<Product> findAllByProductId(String productId) throws InvalidDataAccessException;

    @Query("SELECT p.value, p.date FROM Price p WHERE p.product.id = ?1 AND p.store = ?2")
    Collection<Object[]> findAllPricesByProductIdAndStore(String productId, String store)
            throws InvalidDataAccessException;

    @Query("SELECT p.value, p.date FROM Price p WHERE p.product.id = ?1 AND p.product.category = ?2")
    Collection<Object[]> findAllPricesByProductIdAndProductCategory(String productId, String productCategory)
            throws InvalidDataAccessException;

    @Query("SELECT p.value, p.date FROM Price p WHERE p.product.id = ?1 AND p.product.brand = ?2")
    Collection<Object[]> findAllPricesByProductIdAndBrand(String productId, String brand)
            throws InvalidDataAccessException;

    @Query("SELECT p.value, p.date, p.store FROM Price p WHERE p.product.id = ?1")
    Collection<Object[]> getPricesByProductId(String productId) throws InvalidDataAccessException;
}
