package com.planitsquare.recruitment.api;

import com.planitsquare.recruitment.api.dto.HolidayInfoResponse;
import com.planitsquare.recruitment.application.service.HolidayService;
import com.planitsquare.recruitment.common.base.CursorPaginationInfoReq;
import com.planitsquare.recruitment.common.base.CursorPaginationResult;
import com.planitsquare.recruitment.common.swagger.template.PublicHolidaySwagger;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
public class PublicHolidayController implements PublicHolidaySwagger {

    private final HolidayService holidayService;

    @GetMapping
    public ResponseEntity<CursorPaginationResult<HolidayInfoResponse>> getHolidayInfo(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @Valid @ModelAttribute CursorPaginationInfoReq pageable
    ) {
        return ResponseEntity.ok(
            holidayService.findByConditionWithPagination(year, code, type, from, to, pageable));
    }
}
