package com.diboti.pricecomparatormarket.repo;

import com.diboti.pricecomparatormarket.model.Product;
import com.diboti.pricecomparatormarket.repo.exceptions.InvalidDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    Optional<Product> findById(String productId);

    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Collection<Product> findAllByName(String productName);

    @Query("SELECT p.price, p.date FROM Price p WHERE p.product.id = ?1 AND p.store = ?2")
    Collection<Object[]> findAllPricesByProductIdAndStore(String productId, String store);

    @Query("SELECT p.price, p.date FROM Price p WHERE p.product.id = ?1 AND p.product.category = ?2")
    Collection<Object[]> findAllPricesByProductIdAndProductCategory(String productId, String productCategory);

    @Query("SELECT p.price, p.date FROM Price p WHERE p.product.id = ?1 AND p.product.brand = ?2")
    Collection<Object[]> findAllPricesByProductIdAndBrand(String productId, String brand);

    @Query("SELECT p.price, p.date FROM Price p WHERE p.product.id = ?1")
    Collection<Object[]> getPricesByProductId(String productId);
}
