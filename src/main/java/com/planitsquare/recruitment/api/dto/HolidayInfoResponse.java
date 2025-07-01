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

    private final Long id;
    private final LocalDate date;
    private final String localName;
    private final String name;
    private final String countryName;
    private final HolidayType type;
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
