package com.planitsquare.recruitment.api.dto;

import com.planitsquare.recruitment.domain.entity.HolidayCounty;
import com.planitsquare.recruitment.domain.entity.enums.HolidayType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "공휴일 검색 응답.")
@Getter
public class HolidayInfoResponse {

    @Schema(description = "공휴일 ID", example = "1")
    private final Long id;

    @Schema(description = "공휴일자", example = "2025-01-01")
    private final LocalDate date;

    @Schema(description = "로컬 이름", example = "새해")
    private final String localName;

    @Schema(description = "이름", example = "New Year's Day")
    private final String name;

    @Schema(description = "국가", example = "South Korea")
    private final String countryName;

    @Schema(description = "공휴일 분류", example = "PUBLIC")
    private final HolidayType type;

    @Schema(description = "일부 적용 국가", examples = {"[]"})
    private final List<HolidayCounty> counties;

    public HolidayInfoResponse(
            Long id, LocalDate date, String localName, String name,
            String countryName, HolidayType type, List<HolidayCounty> counties
    ) {
        this.id = id;
        this.date = date;
        this.localName = localName;
        this.name = name;
        this.countryName = countryName;
        this.type = type;
        this.counties = counties;
    }
}
