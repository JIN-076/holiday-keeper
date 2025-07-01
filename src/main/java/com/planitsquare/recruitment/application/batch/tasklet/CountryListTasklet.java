package com.planitsquare.recruitment.application.batch.tasklet;

import java.util.List;

import com.planitsquare.recruitment.common.constant.BatchSql;
import com.planitsquare.recruitment.domain.dto.CountryDto;
import com.planitsquare.recruitment.common.feign.NagerDateFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryListTasklet implements Tasklet {

    private final NagerDateFeignClient nagerDateFeignClient;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        List<CountryDto> dtos = nagerDateFeignClient.getAllAvailableCountries();
        List<Object[]> upserts = dtos.stream()
                .map(dto -> new Object[]{
                    dto.countryCode(), dto.name()
                })
                .toList();
        jdbcTemplate.batchUpdate(BatchSql.UPSERT_COUNTRY, upserts);
        return RepeatStatus.FINISHED;
    }
}
