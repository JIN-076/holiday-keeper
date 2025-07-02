package com.planitsquare.recruitment.application.service;

import com.planitsquare.recruitment.api.dto.HolidayLoadResponse;
import com.planitsquare.recruitment.api.dto.SummaryInfo;
import com.planitsquare.recruitment.domain.repository.HolidayRepository;
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

    private final HolidayRepository holidayRepository;
    private final JobLauncher jobLauncher;
    private final Job holidayJob;

    public HolidayLoadResponse batchRun() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("ts", System.currentTimeMillis())
                .toJobParameters();
        JobExecution exec = jobLauncher.run(holidayJob, jobParameters);
        StepExecution countryExec = exec.getStepExecutions().stream()
            .filter(step -> step.getStepName().equals("countryStep"))
            .findFirst().orElseThrow();
        Long totalCountries = countryExec.getExecutionContext().getLong("totalCountries");
        StepExecution holidayExec = exec.getStepExecutions().stream()
            .filter(step -> step.getStepName().equals("holidayStep"))
            .findFirst().orElseThrow();
        Long totalHolidays = holidayExec.getExecutionContext().getLong("totalHolidays");
        boolean success = holidayExec.getRollbackCount() == 0;
        Long succeed = holidayExec.getCommitCount();
        Long failed = holidayExec.getRollbackCount();
        SummaryInfo summary = SummaryInfo.of(GAP, totalCountries, totalHolidays, succeed, failed);
        return HolidayLoadResponse.of(success, summary);
    }

    public long syncByCondition(String year, String code) {
        if (year == null && code == null) {
            throw new IllegalArgumentException("동기화 기준은 최소 하나 이상 선택해야 합니다.");
        }
        return 0;
    }

    @Transactional("jpaTransactionManager")
    public long deleteByCondition(String year, String code) {
        if (year == null && code == null) {
            throw new IllegalArgumentException("삭제 기준은 최소 하나 이상 선택해야 합니다.");
        }
        return holidayRepository.deleteByCondition(year, code);
    }

}
