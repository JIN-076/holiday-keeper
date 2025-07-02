package com.planitsquare.recruitment.application.service;

import com.planitsquare.recruitment.common.base.CursorPaginationInfoReq;
import com.planitsquare.recruitment.common.base.CursorPaginationResult;
import com.planitsquare.recruitment.domain.entity.enums.HolidayType;
import com.planitsquare.recruitment.util.DateValidator;
import java.time.LocalDate;

import com.planitsquare.recruitment.api.dto.HolidayInfoResponse;
import com.planitsquare.recruitment.domain.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(transactionManager = "jpaTransactionManager", readOnly = true)
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public CursorPaginationResult<HolidayInfoResponse> findByConditionWithPagination(
        String year, String code, String type, LocalDate from, LocalDate to, CursorPaginationInfoReq pageable
    ) {
        DateValidator.validParam(year);
        HolidayType.validType(type);
        if (year != null && (from != null || to != null)) {
            throw new IllegalArgumentException("year와 from~to는 동시에 사용할 수 없습니다.");
        }
        if ((from != null && to != null) && from.isAfter(to)) {
            throw new IllegalArgumentException("from은 to보다 늦을 수 없습니다.");
        }
        return holidayRepository.findByConditionWithPagination(year, code, type, from, to, pageable);
    }

}
