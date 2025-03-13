package com.example.profitforecast.domain;

import com.example.profitforecast.domain.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public record MonthOfAYear(Month month, Year year) {
    private static final String MSG = "Invalid month or year";

    public static MonthOfAYear create(String month, String year) {
        try {
            return new MonthOfAYear(Month.of(Integer.parseInt(month)), Year.parse(year));
        } catch (Exception e) {
            log.warn(MSG + ":" + e.getMessage());
            throw new ValidationException(MSG);
        }
    }

    public List<String> getNPreviousMonths(int n) {
        var result = new ArrayList<String>();
        getNPreviousMonths(result, n, month().getValue(), year().getValue());
        log.info("Getting forecast for months " + String.join(",", result));
        return result;
    }

    private void getNPreviousMonths(List<String> result, int totalMonths, int month, int year) {
        if (totalMonths == result.size()) {
            return;
        }
        for (int i = month - 1; i >= 1; i--) {
            if (result.size() == totalMonths) {
                return;
            }
            result.add(i + "_" + year);
        }
        getNPreviousMonths(result, totalMonths, 13, year - 1);

    }
}
