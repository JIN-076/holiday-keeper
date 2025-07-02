package com.planitsquare.recruitment.api.dto;

import lombok.Getter;

@Getter
public class SummaryInfo {

    private final Long totalYears;
    private final Long totalCountries;
    private final Long totalHolidays;
    private final Long succeedChunks;
    private final Long failedChunks;

    private SummaryInfo(
        Long totalYears, Long totalCountries, Long totalHolidays,
        Long succeedChunks, Long failedChunks
    ) {
        this.totalYears = totalYears;
        this.totalCountries = totalCountries;
        this.totalHolidays = totalHolidays;
        this.succeedChunks = succeedChunks;
        this.failedChunks = failedChunks;
    }

    public static SummaryInfo of(
        Long totalYears, Long totalCountries, Long totalHolidays, Long succeedChunks, Long failedChunks
    ) {
        return new SummaryInfo(totalYears, totalCountries, totalHolidays, succeedChunks, failedChunks);
    }

}
