package com.example.profitforecast.integration.inventory;

import com.example.profitforecast.integration.inventory.model.ProductCost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.example.profitforecast.config.CacheConfig.GET_ALL_PRODUCTS_COST_NAME;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CachedProductCostRepository {

    private final ProductCostRepository productCostRepository;
    private final CaffeineCache getAllProductsCostCache;

    @Cacheable(value = GET_ALL_PRODUCTS_COST_NAME, condition = "@environment.getProperty('app.cache.getAllProductsCost.enable')", unless = "#result.isEmpty()")
    public Map<Long, ProductCost> getAllProductsCost() {
        log.info("Getting products cost from source");
        logCacheStats();
        return productCostRepository.getAllProductsCost()
                .stream()
                .collect(Collectors.toMap(ProductCost::productId, Function.identity()));
    }

    private void logCacheStats() {
        log.info("getAllProductsCostCache stats:" + getAllProductsCostCache.getNativeCache().stats());
    }
}
