package com.diboti.pricecomparatormarket.repo;

import com.diboti.pricecomparatormarket.model.Price;
import com.diboti.pricecomparatormarket.repo.util.PriceForProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface PriceDao extends JpaRepository<Price, Long> {

    Collection<Price> findAllByProduct_Id(String productId);
}
