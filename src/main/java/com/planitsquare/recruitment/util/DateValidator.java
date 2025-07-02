package com.planitsquare.recruitment.util;

import java.time.LocalDate;

public class DateValidator {

    public static void validParam(String year) {
        if (year == null) return;
        int y;
        try {
            y = Integer.parseInt(year);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력 값입니다. 연도를 입력해주세요.");
        }
        int current = LocalDate.now().getYear();
        if (y < (current - 5) || y > current) {
            throw new IllegalArgumentException("최근 5년에 대해서만 조회할 수 있습니다.");
        }
    }

}
