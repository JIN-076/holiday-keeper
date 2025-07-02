package com.planitsquare.recruitment.api.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class HolidayLoadResponse {

    private final LocalDateTime timestamp;
    private final Boolean success;
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
