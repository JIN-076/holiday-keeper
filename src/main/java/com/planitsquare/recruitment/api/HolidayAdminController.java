package com.planitsquare.recruitment.api;

import com.planitsquare.recruitment.api.dto.HolidayDeleteResponse;
import com.planitsquare.recruitment.api.dto.HolidayLoadResponse;
import com.planitsquare.recruitment.api.dto.HolidaySyncResponse;
import com.planitsquare.recruitment.application.service.HolidayAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/holidays")
@RequiredArgsConstructor
public class HolidayAdminController {

    private static final Long GAP = 5L;
    private final HolidayAdminService holidayAdminService;

    @PostMapping("/load")
    public ResponseEntity<HolidayLoadResponse> loadHolidays() {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(holidayAdminService.batchRun());
    }

    @PutMapping("/sync")
    public ResponseEntity<HolidaySyncResponse> syncHolidays(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String code
    ) {
        return ResponseEntity.ok(
                holidayAdminService.syncByCondition(year, code, GAP)
        );
    }

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
