package com.planitsquare.recruitment.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.planitsquare.recruitment.domain.entity.enums.HolidayType;

import java.time.LocalDate;
import java.util.List;

public record HolidayDto(
    String countryCode,
    @JsonProperty("date") LocalDate date,
    String localName,
    String name,
    boolean fixed,
    @JsonProperty("types") @JsonSetter(nulls = Nulls.AS_EMPTY) List<HolidayType> types,
    boolean global,
    Integer launchYear,
    @JsonProperty("counties") @JsonSetter(nulls = Nulls.AS_EMPTY) List<String> counties) {

}
