package com.planitsquare.recruitment.common.base;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.springframework.data.domain.Sort.Direction;

import java.util.Objects;

@Getter
public class CursorPaginationInfoReq {

    private final Long cursorId;

    @Min(1)
    private final int pageSize;

    private final Direction sortOrder;

    public CursorPaginationInfoReq(@Nullable Long cursorId, int pageSize, @Nullable Direction sortOrder) {
        this.cursorId = cursorId;
        this.pageSize = pageSize;
        this.sortOrder = Objects.requireNonNullElse(sortOrder, Direction.DESC);
    }
}
