package com.planitsquare.recruitment.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "적재/동기화된 데이터 통계 응답")
@Getter
public class SummaryInfo {

    @Schema(description = "적재/동기화한 년도 수", example = "5")
    private final Long totalYears;

    @Schema(description = "적재/동기화한 국가 수", example = "117")
    private final Long totalCountries;

    @Schema(description = "최종 삽입/수정된 공휴일 수", example = "9000")
    private final Long totalHolidays;

    @Schema(description = "적재/동기화에 성공한 chunk 수", example = "25")
    private final Long succeedChunks;

    @Schema(description = "적재/동기화에 실패한 chunk 수, (재시도 3회 포함)", example = "0")
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
