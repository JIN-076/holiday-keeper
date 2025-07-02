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

    /**
     *
     * @param year 검색하려는 연도
     * @param code 검색하려는 국가코드
     * @param type 검색하려는 휴일 분류
     * @param from 검색하려는 기간 (from ~ to) *year과 from~to는 동시에 사용하지 못하도록 제약 추가 (충돌 방지)
     * @param to
     * @param pageable 커서 기반 페이지네이션을 위한 req (기본으로 id DESC 순으로 정렬, ASC 방식 지원 X)
     * @return CursorPaginationResult<HolidayInfoResponse>
     *
     * 공휴일 데이터를 조회하는 경우,
     *  1. 계층 구조를 사용하면 유연하게 필터링을 할 수가 없다고 판단
     *  2. @RequestBody 방식을 고민하였으나, 직관/명시적이면서 대부분의 검색포털이 채택하고 있는 쿼리 파라미터 방식 선정
     * QueryDsl을 활용한 동적 설계를 통해 유연한 필터링 조합이 가능하도록 설계
     *
     */

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
