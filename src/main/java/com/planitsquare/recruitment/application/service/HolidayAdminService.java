package com.planitsquare.recruitment.application.service;

import com.planitsquare.recruitment.domain.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HolidayAdminService {

    private final HolidayRepository holidayRepository;
    private final JobLauncher jobLauncher;
    private final Job holidayJob;

    public void batchRun() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("ts", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(holidayJob, jobParameters);
    }

    @Transactional("jpaTransactionManager")
    public long deleteByCondition(String year, String code) {
        if (year == null && code == null) {
            throw new IllegalArgumentException("삭제 기준은 최소 하나 이상 선택해야 합니다.");
        }
        return holidayRepository.deleteByCondition(year, code);
    }

}
