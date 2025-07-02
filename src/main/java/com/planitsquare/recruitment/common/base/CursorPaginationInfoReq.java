package com.planitsquare.recruitment.common.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.springframework.data.domain.Sort.Direction;

import java.util.Objects;

@Schema(description = "페이지네이션 요청")
@Getter
public class CursorPaginationInfoReq {

    @Schema(description = "커서 ID", examples = "null")
    private final Long cursorId;

    @Min(1)
    @Schema(description = "페이지 수", examples = "5")
    private final int pageSize;

    @Schema(description = "정렬 순서", examples = "", defaultValue = "DESC")
    private final Direction sortOrder;

    public CursorPaginationInfoReq(@Nullable Long cursorId, int pageSize, @Nullable Direction sortOrder) {
        this.cursorId = cursorId;
        this.pageSize = pageSize;
        this.sortOrder = Objects.requireNonNullElse(sortOrder, Direction.DESC);
    }
}
