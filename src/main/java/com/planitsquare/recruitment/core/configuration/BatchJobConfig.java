package com.planitsquare.recruitment.core.configuration;

import com.planitsquare.recruitment.common.constant.BatchSql;
import com.planitsquare.recruitment.domain.entity.Country;
import com.planitsquare.recruitment.application.batch.tasklet.CountryListTasklet;
import com.planitsquare.recruitment.domain.repository.CountryRepository;
import com.planitsquare.recruitment.application.batch.vo.CountryYear;
import com.planitsquare.recruitment.domain.dto.HolidayDto;
import com.planitsquare.recruitment.common.feign.NagerDateFeignClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {

    @Bean
    public Job holidayJob(JobRepository jobRepository, Step countryStep, Step holidayStep) {
        return new JobBuilder("holidayDataLoadJob", jobRepository)
            .start(countryStep)
            .next(holidayStep)
            .build();
    }

    @Bean
    public Step holidayStep(
        JobRepository jobRepository,
        @Qualifier("transactionManager") PlatformTransactionManager transactionManager,
        ItemReader<CountryYear> reader,
        ItemProcessor<CountryYear, List<HolidayDto>> processor,
        ItemWriter<List<HolidayDto>> writer
    ) {
        return new StepBuilder("holidayStep", jobRepository)
            .<CountryYear, List<HolidayDto>>chunk(30, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .faultTolerant()
                .retry(RuntimeException.class)
                .retry(Exception.class)
            .retryLimit(3)
            .build();
    }

    @Bean
    public Step countryStep(
        JobRepository jobRepository,
        @Qualifier("transactionManager") PlatformTransactionManager transactionManager,
        CountryListTasklet tasklet
    ) {
        return new StepBuilder("countryStep", jobRepository)
            .tasklet(tasklet, transactionManager)
            .build();
    }

    @Bean
    @StepScope
    public ItemReader<CountryYear> reader(
        CountryRepository countryRepository
    ) {
        int currentYear = LocalDate.now().getYear();
        int startYear = currentYear - 5;

        List<Country> countries = countryRepository.findAll();
        List<CountryYear> combos = countries.stream()
            .flatMap(country -> IntStream.rangeClosed(startYear, currentYear)
            .mapToObj(year -> new CountryYear(year, country.getCountryCode())))
            .toList();
        return new ListItemReader<>(combos);
    }

    @Bean
    public ItemProcessor<CountryYear, List<HolidayDto>> processor(
        NagerDateFeignClient nagerDateFeignClient
    ) {
        return combo -> nagerDateFeignClient.getHolidaysByCondition(
            combo.year(), combo.countryCode()
        );
    }

    @Bean
    public ItemWriter<List<HolidayDto>> writer(JdbcTemplate jdbcTemplate) {
        return chunk -> {
            List<? extends List<HolidayDto>> chunks = chunk.getItems();
            List<HolidayDto> dtos = chunks.stream().flatMap(List::stream).toList();

            List<Object[]> upserts = dtos.stream()
                .map(dto -> new Object[]{
                    dto.date(),
                    dto.fixed(),
                    dto.global(),
                    dto.countryCode(),
                    dto.localName(),
                    dto.name(),
                    dto.types().getFirst().toString(),
                    dto.launchYear()
                })
                .toList();
            jdbcTemplate.batchUpdate(BatchSql.UPSERT_HOLIDAY, upserts);

            String selectSql = createSql(dtos);

            Object[] selects = dtos.stream()
                    .flatMap(dto -> Stream.of(
                            dto.countryCode(),
                            dto.date(),
                            dto.localName()
                    ))
                    .toArray();
            List<Map<String,Object>> rows = jdbcTemplate.queryForList(selectSql, selects);

            Map<String, Long> ids = new HashMap<>(rows.size());
            for (Map<String,Object> row : rows) {
                String key = generateKey(row);
                ids.put(key, ((Number)row.get("id")).longValue());
            }

            List<Object[]> inserts = new ArrayList<>();
            for (HolidayDto dto : dtos) {
                String key = generateKey(dto);
                Long id = ids.get(key);
                if (id == null) {
                    log.warn("No holiday_id found for [{}]", key);
                    throw new RuntimeException("No holiday_id found for [" + key + "]");
                }
                for (String county : dto.counties()) {
                    inserts.add(new Object[]{ id, county });
                }
            }
            if (!inserts.isEmpty()) {
                jdbcTemplate.batchUpdate(BatchSql.UPSERT_COUNTY, inserts);
            }

            jdbcTemplate.batchUpdate(BatchSql.DELETE_COUNTY);
        };
    }

    private String createSql(List<HolidayDto> dtos) {
        String inClause = dtos.stream()
                .map(dto -> "(?, ?, ?)")
                .collect(Collectors.joining(", "));
        return String.format(BatchSql.SELECT_HOLIDAY, inClause);
    }

    private String generateKey(Map<String, Object> row) {
        return row.get("country_code") +
                BatchSql.pipe +
                row.get("date") +
                BatchSql.pipe +
                row.get("local_name");
    }

    private String generateKey(HolidayDto dto) {
        return dto.countryCode() +
                BatchSql.pipe +
                dto.date() +
                BatchSql.pipe +
                dto.localName();
    }
}
