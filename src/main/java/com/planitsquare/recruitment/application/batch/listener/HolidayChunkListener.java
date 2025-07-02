package com.planitsquare.recruitment.application.batch.listener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HolidayChunkListener implements ChunkListener {

    @Getter
    private int successCount = 0;
    private int failureCount = 0;

    @Override
    public void beforeChunk(ChunkContext context) {
        ChunkListener.super.beforeChunk(context);
    }

    @Override
    public void afterChunk(ChunkContext context) {
        successCount++;
        log.info("Chunk succeeded. totalSuccess: {}", successCount);
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        failureCount++;
        log.info("Chunk failed. totalFailure: {}", failureCount);
    }

}
