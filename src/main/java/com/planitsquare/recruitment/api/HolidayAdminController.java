package com.planitsquare.recruitment.api;

import com.planitsquare.recruitment.api.dto.HolidayDeleteResponse;
import com.planitsquare.recruitment.api.dto.HolidayLoadResponse;
import com.planitsquare.recruitment.api.dto.HolidaySyncResponse;
import com.planitsquare.recruitment.application.service.HolidayAdminService;
import com.planitsquare.recruitment.common.swagger.template.HolidayAdminSwagger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
public class HolidayAdminController implements HolidayAdminSwagger {

    private static final Long GAP = 5L;
    private final HolidayAdminService holidayAdminService;

    /**
     * (현재 날짜를 기준으로) 최근 5년 X 전세계 국가의 공휴일 데이터를 적재하는 API
     * @return HolidayLoadResponse
     *
     * Spring Batch를 활용하여 데이터를 적재하도록 설계
     * bulkInsert 방식을 사용하기 위해 배치 작업에서는 JPA가 아닌 JdbcTemplate을 활용
     * 1. GET 국가 목록 API를 가져와서 Country 테이블에 저장하는 Tasklet 등록 (공휴일 데이터를 적재하기 전 1번만 실행)
     * 2. GET 공휴일 목록 API를 가져와서 chunk 기반으로 여러 번에 걸쳐서 Holiday, Holiday_county 테이블에 저장하는 Step 등록
     *
     * 최초 실행 시, 국가 및 공휴일 데이터 일괄 적재
     * 이후 실행 시, 기존 데이터에 덮어쓰는 동기화 방식으로 동작
     */

    @PostMapping("/import")
    public ResponseEntity<HolidayLoadResponse> loadHolidays() {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(holidayAdminService.batchRun());
    }

    /**
     * 연도별 / 국가별 공휴일 데이터를 재동기화하는 API (쿼리 파라미터를 활용하여 직관적이고 유연하게 필터링을 조합해서 동기화 가능)
     * @param year 동기화하려는 연도
     * @param code 동기화하려는 국가 코드
     * @return HolidaySyncResponse
     *
     * if year = null & code = null -> 최근 5년 X 전세계 국가에 대한 공휴일을 재동기화
     * if year = null & code != null -> 해당 연도의 전세계 국가에 대한 공휴일을 재동기화
     * if year != null & code = null -> 해당 국가의 최근 5년에 대한 공휴일을 재동기화
     *
     * 동기화 방식은 기존의 데이터와 비교 후 변경된 데이터를 upsertg하는 방식과, 새로 데이터를 적재하는 staging 테이블과 물리적 스왑 방식을 고민
     * 5년 X 전세계 국가의 공휴일에 대한 데이터는 대략 10,000건 정도로, 일일이 덮어쓰더라도 성능에 큰 무리가 될 정도의 규모가 아니라고 판단
     */

    @PutMapping("/sync")
    public ResponseEntity<HolidaySyncResponse> syncHolidays(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String code
    ) {
        return ResponseEntity.ok(
                holidayAdminService.syncByCondition(year, code, GAP)
        );
    }

    /**
     * 연도별 / 국가별 공휴일 데이터를 전체 삭제하는 API (쿼리 파라미터를 활용하여 직관적이고 유연하게 필터링을 조합하여 삭제 가능)
     * @param year 삭제하려는 연도
     * @param code 삭제하려는 국가코드
     * @return HolidayDeleteResponse
     *
     * if year = null & code = null -> 모든 데이터를 날리는 것은 위험하다고 판단하여 지원하지 않도록 설계
     * if year = null & code != null -> 해당 연도의 전세계 국가에 대한 공휴일을 일괄 삭제
     * if year != null & code = null -> 해당 국가의 최근 5년에 대한 공휴일을 일괄 삭제
     *
     * 삭제를 위한 동적 필터링을 유연하게 가져가면서 재사용성을 위해 QueryDsl을 이용한 동적 쿼리로 설계 (자식 삭제 후 부모 삭제)
     * JPA에서 제공하는 cascade, orphan 옵션을 이용하는 방안도 고민했으나, 유연한 필터링을 가져갈 경우 해당 옵션을 활용하지 못하기에 채택하지 않음
     */

    @DeleteMapping
    public ResponseEntity<HolidayDeleteResponse> deleteHolidays(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String code
    ) {
        return ResponseEntity.ok(
            HolidayDeleteResponse.from(holidayAdminService.deleteByCondition(year, code))
        );
    }
}
