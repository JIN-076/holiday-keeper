package com.planitsquare.recruitment.application.service;

import static com.planitsquare.recruitment.exception.code.ErrorCode500.INTERNAL_SERVER_ERROR;

import com.planitsquare.recruitment.api.dto.HolidayLoadResponse;
import com.planitsquare.recruitment.api.dto.HolidaySyncResponse;
import com.planitsquare.recruitment.api.dto.SummaryInfo;
import com.planitsquare.recruitment.domain.repository.HolidayRepository;
import com.planitsquare.recruitment.exception.batch.BatchExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HolidayAdminService {

    private static final Long GAP = 5L;
    private static final String COUNTRY_STEP = "countryStep";
    private static final String HOLIDAY_STEP = "holidayStep";
    private static final String TOTAL_COUNTRY = "totalCountries";
    private static final String TOTAL_HOLIDAY = "totalHolidays";

    private final HolidayRepository holidayRepository;
    private final JobLauncher jobLauncher;
    private final Job holidayJob;

    public HolidayLoadResponse batchRun() {
        JobExecution exec = reloadWithCondition(null, null);
        SummaryInfo summary = makeSummaryWithStepExecution(exec);
        boolean succeed = summary.getFailedChunks() == 0L;
        return HolidayLoadResponse.of(succeed, summary);
    }

    public HolidaySyncResponse syncByCondition(String year, String code) {
        JobExecution exec = reloadWithCondition(year, code);
        SummaryInfo summary = makeSummaryWithStepExecution(exec);
        boolean succeed = !exec.getStatus().isUnsuccessful();
        return HolidaySyncResponse.from(succeed, summary);
    }

    @Transactional("jpaTransactionManager")
    public long deleteByCondition(String year, String code) {
        if (year == null && code == null) {
            throw new IllegalArgumentException("삭제 기준은 최소 하나 이상 선택해야 합니다.");
        }
        return holidayRepository.deleteByCondition(year, code);
    }

    private JobExecution reloadWithCondition(String year, String code) {
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addLong("run.id", System.currentTimeMillis());
        if (code != null && !code.isBlank()) builder.addString("countryCode", code);
        if (year != null) builder.addLong("year", Long.parseLong(year));
        JobParameters jobParameters = builder.toJobParameters();
        try {
            return jobLauncher.run(holidayJob, jobParameters);
        } catch (Exception e) {
            throw new BatchExecutionException(INTERNAL_SERVER_ERROR);
        }
    }


    private SummaryInfo makeSummaryWithStepExecution(JobExecution execution) {
        StepExecution countryExec = fetchStepExecutionWithStepName(execution, COUNTRY_STEP);
        Long totalCountries = countryExec.getExecutionContext().getLong(TOTAL_COUNTRY);

        StepExecution holidayExec = fetchStepExecutionWithStepName(execution, HOLIDAY_STEP);
        Long totalHolidays = holidayExec.getExecutionContext().getLong(TOTAL_HOLIDAY);

        Long succeed = holidayExec.getCommitCount();
        Long failed = holidayExec.getRollbackCount();
        return SummaryInfo.of(GAP, totalCountries, totalHolidays, succeed, failed);
    }

    private StepExecution fetchStepExecutionWithStepName(JobExecution execution, String stepName) {
        return execution.getStepExecutions().stream()
            .filter(step -> step.getStepName().equals(stepName))
            .findFirst().orElseThrow();
    }

}
