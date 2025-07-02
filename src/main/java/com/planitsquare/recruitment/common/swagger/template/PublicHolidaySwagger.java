package com.planitsquare.recruitment.common.swagger.template;

import com.planitsquare.recruitment.api.dto.HolidayInfoResponse;
import com.planitsquare.recruitment.common.base.CursorPaginationInfoReq;
import com.planitsquare.recruitment.common.base.CursorPaginationResult;
import com.planitsquare.recruitment.common.swagger.error.SwaggerPublicHolidayErrorExamples;
import com.planitsquare.recruitment.exception.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Public Holiday", description = "사용자용 공휴일 관련 API")
@SuppressWarnings("unused")
public interface PublicHolidaySwagger {

    @Operation(summary = "공휴일 검색")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "공휴일 검색 성공"),
        @ApiResponse(responseCode = "400", description = "요청한 데이터가 유효하지 않음",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class),
                examples = {
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.INVALID_INPUT_ARG),
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.BAD_FROM_TO_REQ),
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.MISSING_FROM_TO_REQ),
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.INVALID_YEAR_ARG),
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.UNSUPPORTED_YEAR_ARG),
                    @ExampleObject(name = "E000002", value = SwaggerPublicHolidayErrorExamples.INVALID_TYPE_ARG)
                }
            ))
        }
    )
    ResponseEntity<CursorPaginationResult<HolidayInfoResponse>> getHolidayInfo(
        @RequestParam(required = false) String year,
        @RequestParam(required = false) String code,
        @RequestParam(required = false) String type,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
        @Valid @ModelAttribute CursorPaginationInfoReq pageable
    );

}
