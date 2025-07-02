package com.planitsquare.recruitment.common.swagger.error;

public class SwaggerPublicHolidayErrorExamples {

    public static final String INVALID_INPUT_ARG = "{\"timestamp\":\"2025-07-02T12:00:00.404Z\",\"statusCode\":400,\"code\":\"E000002\",\"message\":\"year와 from~to는 동시에 사용할 수 없습니다.\"}";
    public static final String BAD_FROM_TO_REQ = "{\"timestamp\":\"2025-07-02T12:00:00.404Z\",\"statusCode\":400,\"code\":\"E000002\",\"message\":\"from은 to보다 늦을 수 없습니다.\"}";
    public static final String MISSING_FROM_TO_REQ = "{\"timestamp\":\"2025-07-02T12:00:00.404Z\",\"statusCode\":400,\"code\":\"E000002\",\"message\":\"from, to 입력 값을 모두 입력해야 합니다.\"}";
    public static final String INVALID_YEAR_ARG = "{\"timestamp\":\"2025-07-02T12:00:00.404Z\",\"statusCode\":400,\"code\":\"E000002\",\"message\":\"잘못된 입력 값입니다. 연도를 입력해주세요.\"}";
    public static final String UNSUPPORTED_YEAR_ARG = "{\"timestamp\":\"2025-07-02T12:00:00.404Z\",\"statusCode\":400,\"code\":\"E000002\",\"message\":\"최근 5년에 대해서만 조회할 수 있습니다.\"}";
    public static final String INVALID_TYPE_ARG = "{\"timestamp\":\"2025-07-02T12:00:00.404Z\",\"statusCode\":400,\"code\":\"E000002\",\"message\":\"요청하신 국가 코드는 존재하지 않습니다.\"}";

    private SwaggerPublicHolidayErrorExamples() {}

}
