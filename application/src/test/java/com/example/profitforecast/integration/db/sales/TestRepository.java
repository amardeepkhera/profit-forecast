package com.example.profitforecast.integration.db.sales;

import com.example.profitforecast.integration.db.sales.entity.Product;
import com.example.profitforecast.integration.db.sales.entity.Sales;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TestRepository {

    private static final RandomStringUtils RANDOM_STRING_UTILS = RandomStringUtils.secure();
    private final ProductRepository productRepository;
    private final SalesRepository salesRepository;

    public TestProduct getAppleIphone15() {
        return productRepository.findById(1L)
                .map(product -> new TestProduct(product, new BigDecimal("1099")))
                .get();
    }

    public TestProduct getAppleIphone15Pro() {
        return productRepository.findById(2L)
                .map(product -> new TestProduct(product, new BigDecimal("1299")))
                .get();
    }

    public TestProduct getGooglePixel7() {
        return productRepository.findById(3L)
                .map(product -> new TestProduct(product, new BigDecimal("999")))
                .get();
    }

    public TestProduct getSamsungGalaxyFlip() {
        return productRepository.findById(6L)
                .map(product -> new TestProduct(product, new BigDecimal("1199")))
                .get();
    }

    public TestProduct createProduct(String brand) {
        var product = new Product();
        product.setBrand(brand);
        product.setFamily(RANDOM_STRING_UTILS.nextAlphabetic(3));
        product.setModel(RANDOM_STRING_UTILS.nextAlphabetic(3));
        return new TestProduct(productRepository.save(product), null);
    }

    public Sales createSale(Product product, Month month, Year year, Long unitsSold, BigDecimal totalDollarSales) {
        var sale = new Sales();
        sale.setProduct(product);
        sale.setMonth(month.getValue());
        sale.setYear(year.getValue());
        sale.setUnitsSold(unitsSold);
        sale.setTotalDollarSales(totalDollarSales);
        return salesRepository.save(sale);
    }

    public void deleteProducts(List<TestProduct> products) {
        productRepository.deleteAll(products.stream().map(TestProduct::entity).toList());
    }

    public void deleteSale(List<Sales> sales) {
        salesRepository.deleteAll(sales);
    }

    public record TestProduct(Product entity, BigDecimal costPrice) {
    }
}
