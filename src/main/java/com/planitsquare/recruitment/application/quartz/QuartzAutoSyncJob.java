package com.planitsquare.recruitment.application.quartz;

import com.planitsquare.recruitment.application.service.HolidayAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DisallowConcurrentExecution
@RequiredArgsConstructor
public class QuartzAutoSyncJob implements Job {

    private static final Long GAP = 1L;
    private final HolidayAdminService holidayAdminService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("QuartzAutoSyncJob start at {}", System.currentTimeMillis());
        holidayAdminService.syncByCondition(null, null, GAP);
        log.info("QuartzAutoSyncJob end at {}", System.currentTimeMillis());
    }
}
