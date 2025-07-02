package com.planitsquare.recruitment.application.batch.listener;

import com.planitsquare.recruitment.domain.dto.HolidayDto;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
public class HolidayCountListener
    implements ItemWriteListener<List<HolidayDto>>, StepExecutionListener {

    private int count;

    @Override
    public void beforeWrite(Chunk<? extends List<HolidayDto>> items) {
        for (List<HolidayDto> chunk : items) {
            count += chunk.size();
        }
    }

    @Override
    public void afterWrite(Chunk<? extends List<HolidayDto>> items) {
        ItemWriteListener.super.afterWrite(items);
    }

    @Override
    public void onWriteError(Exception exception, Chunk<? extends List<HolidayDto>> items) {
        ItemWriteListener.super.onWriteError(exception, items);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        count = 0;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().putLong("totalHolidays", count);
        return stepExecution.getExitStatus();
    }
}
