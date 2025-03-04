package com.example.profitforecast.integration.db.sales;

import com.example.profitforecast.domain.MonthOfAYear;
import com.example.profitforecast.integration.db.sales.entity.ProductSales;
import com.example.profitforecast.integration.db.sales.entity.Sales;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends CrudRepository<Sales, Long> {

    @Query(value = "select p.id as productId, p.brand as brand, AVG(s.units_sold) as avgUnitsSold," +
            " AVG(s.total_dollar_sales) as avgTotalSales" +
            " from sales s, product p" +
            " WHERE s.product_id =p.id" +
            " and s.year =:#{#monthOfAYear.year().getValue()}" +
            " and s.month IN (:#{#monthOfAYear.getNPreviousMonths(#previousNMonths)})" +
            " group by p.brand,p.id" +
            " order by p.brand"
            , nativeQuery = true)
    List<ProductSales> getAllProductsSales(@Param("monthOfAYear") MonthOfAYear monthOfAYear, int previousNMonths);

}
