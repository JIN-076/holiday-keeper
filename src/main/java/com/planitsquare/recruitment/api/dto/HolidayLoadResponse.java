package com.planitsquare.recruitment.api.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "데이터 적재 응답")
@Getter
public class HolidayLoadResponse {

    @Schema(description = "데이터 적재 완료된 timestamp")
    private final LocalDateTime timestamp;

    @Schema(description = "일괄 적재 성공 여부, 한 chunk라도 실패 시, 실패로 간주")
    private final Boolean success;

    @Schema(description = "적재 요약")
    private final SummaryInfo summary;

    private HolidayLoadResponse(Boolean success, SummaryInfo summary) {
        this.timestamp = LocalDateTime.now();
        this.success = success;
        this.summary = summary;
    }

    public static HolidayLoadResponse of(Boolean success, SummaryInfo summary) {
        return new HolidayLoadResponse(success, summary);
    }


}
