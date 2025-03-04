package com.example.profitforecast.controller;

import com.example.profitforecast.domain.MonthOfAYear;
import com.example.profitforecast.domain.model.ProfitForecastPerBrand;
import com.example.profitforecast.service.ForecastService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("forecast")
@RequiredArgsConstructor
public class ForecastController {

    private final ForecastService forecastService;

    @GetMapping("brands")
    List<ProfitForecastPerBrand> getProfitForecastForAllBrands(@RequestParam("month") String month, @RequestParam("year") String year) {
        log.info("Received get profit forecast request for month {} and year {}", month, year);
        var monthOfAYear = MonthOfAYear.create(month, year);
        return forecastService.getProfitForecastForAllBrands(monthOfAYear);
    }
}
