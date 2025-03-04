package com.example.profitforecast.domain;

import com.example.profitforecast.domain.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

import java.time.Month;
import java.time.Year;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public Set<Integer> getNPreviousMonths(int n) {
        return IntStream.range(month().getValue() - n, month.getValue())
                .boxed()
                .filter(month -> month > 0 && month <= 12)
                .collect(Collectors.toSet());
    }
}
