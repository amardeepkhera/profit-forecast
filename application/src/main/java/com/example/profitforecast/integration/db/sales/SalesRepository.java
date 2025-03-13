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

    @Query(value = "select temp.brand as brand,temp.productId as productId," +
            " AVG(temp.units_sold) as avgUnitsSold, AVG(temp.total_dollar_sales) as avgTotalSales" +
            " from (" +
            " select p.id as productId, p.brand as brand," +
            " s.units_sold as units_sold," +
            " s.total_dollar_sales as total_dollar_sales," +
            " CONCAT(s.month,'_',s.year) as monthYr" +
            " from sales s, product p" +
            " WHERE s.product_id =p.id" +
            " group by p.brand,p.id,s.month,s.year,s.units_sold,s.total_dollar_sales " +
            ") temp" +
            " where temp.monthYr in (:#{#monthOfAYear.getNPreviousMonths(#previousNMonths)})" +
            " group by temp.brand,temp.productId order by temp.brand"
            , nativeQuery = true)
    List<ProductSales> getAllProductsSales(@Param("monthOfAYear") MonthOfAYear monthOfAYear, int previousNMonths);

}
