package com.planitsquare.recruitment.domain.repository;

import com.planitsquare.recruitment.common.base.CursorPaginationInfoReq;
import com.planitsquare.recruitment.common.base.CursorPaginationResult;
import com.planitsquare.recruitment.api.dto.HolidayInfoResponse;

import java.time.LocalDate;

public interface HolidayRepositoryCustom {

    CursorPaginationResult<HolidayInfoResponse> findByConditionWithPagination(
            String year, String code, String type,
            LocalDate from, LocalDate to,
            CursorPaginationInfoReq pageable
    );

}
