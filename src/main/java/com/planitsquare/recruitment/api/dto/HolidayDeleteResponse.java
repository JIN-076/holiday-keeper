package com.planitsquare.recruitment.api.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class HolidayDeleteResponse {

    private final LocalDateTime timestamp;
    private final Long deletedCount;

    private HolidayDeleteResponse(Long deletedCount) {
        this.timestamp = LocalDateTime.now();
        this.deletedCount = deletedCount;
    }

    public static HolidayDeleteResponse from(Long deletedCount) {
        return new HolidayDeleteResponse(deletedCount);
    }

}
