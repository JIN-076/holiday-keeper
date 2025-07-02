package com.planitsquare.recruitment.api.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class HolidaySyncResponse {

    private final Boolean success;
    private final LocalDateTime timestamp;
    private final SummaryInfo summary;

    private HolidaySyncResponse(Boolean success, SummaryInfo summary) {
        this.timestamp = LocalDateTime.now();
        this.success = success;
        this.summary = summary;
    }

    public static HolidaySyncResponse from(Boolean success, SummaryInfo summary) {
        return new HolidaySyncResponse(success, summary);
    }

}
