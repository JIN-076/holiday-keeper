package com.planitsquare.recruitment.api.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "공휴일 데이터 삭제 응답")
@Getter
public class HolidayDeleteResponse {

    @Schema(description = "데이터가 삭제 완료된 timestamp")
    private final LocalDateTime timestamp;

    @Schema(description = "삭제된 공휴일 레코드 수")
    private final Long deletedCount;

    private HolidayDeleteResponse(Long deletedCount) {
        this.timestamp = LocalDateTime.now();
        this.deletedCount = deletedCount;
    }

    public static HolidayDeleteResponse from(Long deletedCount) {
        return new HolidayDeleteResponse(deletedCount);
    }

}
