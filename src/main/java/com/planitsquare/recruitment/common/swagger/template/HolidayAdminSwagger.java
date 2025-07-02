package com.planitsquare.recruitment.common.swagger.template;

import com.planitsquare.recruitment.api.dto.HolidayDeleteResponse;
import com.planitsquare.recruitment.api.dto.HolidayLoadResponse;
import com.planitsquare.recruitment.api.dto.HolidaySyncResponse;
import com.planitsquare.recruitment.common.swagger.error.SwaggerAdminHolidayErrorExamples;
import com.planitsquare.recruitment.common.swagger.error.SwaggerPublicHolidayErrorExamples;
import com.planitsquare.recruitment.exception.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Admin Holiday", description = "관리자용 공휴일 관련 API")
@SuppressWarnings("unused")
public interface HolidayAdminSwagger {

    @Operation(summary = "최근 5년간 전세계 공휴일 데이터 적재")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "데이터 적재 성공",
            content = @Content(schema = @Schema(implementation = HolidayLoadResponse.class)))
    })
    ResponseEntity<HolidayLoadResponse> loadHolidays();

    @Operation(summary = "연도별 / 국가별 공휴일 데이터 재동기화")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "데이터 동기화 성공",
            content = @Content(schema = @Schema(implementation = HolidaySyncResponse.class))),
        @ApiResponse(responseCode = "400", description = "요청한 데이터가 유효하지 않음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.INVALID_YEAR_ARG),
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.UNSUPPORTED_YEAR_ARG)
                }
            )),
        @ApiResponse(responseCode = "404", description = "요청한 데이터가 존재하지 않음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @ExampleObject(name = "E000002", value = SwaggerAdminHolidayErrorExamples.COUNTRY_NOT_FOUND)
                }
            ))
    })
    ResponseEntity<HolidaySyncResponse> syncHolidays(
        @RequestParam(required = false) String year,
        @RequestParam(required = false) String code
    );

    @Operation(summary = "연도별 / 국가별 공휴일 일괄 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공휴일 삭제 성공",
            content = @Content(schema = @Schema(implementation = HolidayDeleteResponse.class))),
        @ApiResponse(responseCode = "400", description = "요청한 데이터가 유효하지 않음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.INVALID_YEAR_ARG),
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.UNSUPPORTED_YEAR_ARG)
                }
            ))
    })
    ResponseEntity<HolidayDeleteResponse> deleteHolidays(
        @RequestParam(required = false) String year,
        @RequestParam(required = false) String code
    );

}
