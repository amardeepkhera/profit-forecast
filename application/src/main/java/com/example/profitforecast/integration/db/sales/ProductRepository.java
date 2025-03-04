package com.example.profitforecast.integration.db.sales;

import com.example.profitforecast.integration.db.sales.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
