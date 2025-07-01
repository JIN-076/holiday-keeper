package com.planitsquare.recruitment.api;

import com.planitsquare.recruitment.application.service.HolidayAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/holidays")
@RequiredArgsConstructor
public class HolidayAdminController {

    private final HolidayAdminService holidayAdminService;

    @PostMapping("/load")
    public ResponseEntity<Void> loadHolidays() throws Exception {
        holidayAdminService.batchRun();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteHolidays(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String code
    ) {
        return ResponseEntity.ok(
            holidayAdminService.deleteByCondition(year, code)
        );
    }
}
