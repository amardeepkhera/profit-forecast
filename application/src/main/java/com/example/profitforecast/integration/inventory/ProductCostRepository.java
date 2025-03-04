package com.example.profitforecast.integration.inventory;

import com.example.profitforecast.config.properties.InventoryConfig;
import com.example.profitforecast.integration.inventory.model.ProductCost;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class ProductCostRepository {

    private final WebClient inventoryClient;
    private final InventoryConfig inventoryConfig;

    public ProductCostRepository(@Qualifier("inventoryClient") WebClient inventoryClient, InventoryConfig inventoryConfig) {
        this.inventoryClient = inventoryClient;
        this.inventoryConfig = inventoryConfig;
    }

    public List<ProductCost> getAllProductsCost() {
        return inventoryClient
                .get()
                .uri(inventoryConfig.getAllProductsCostUrl())
                .retrieve()
                .bodyToFlux(ProductCost.class)
                .collectList()
                .block();
    }
}
