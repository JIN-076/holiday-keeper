package com.planitsquare.recruitment.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum HolidayType {

    PUBLIC("법정 공휴일"),
    BANK("은행 휴업일"),
    SCHOOL("학교 휴일"),
    OBSERVANCE("기념일"),
    AUTHORITIES("관공서 휴업일"),
    OPTIONAL("휴일"),
    ;

    private final String description;

    HolidayType(String description) {
        this.description = description;
    }

    @JsonCreator
    public static HolidayType from(String value) {
        return HolidayType.valueOf(value.toUpperCase());
    }

    public static void validType(String type) {
        if (type == null) return;
        try {
            HolidayType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("지원되지 않는 휴일 분류입니다.");
        }
    }

}
