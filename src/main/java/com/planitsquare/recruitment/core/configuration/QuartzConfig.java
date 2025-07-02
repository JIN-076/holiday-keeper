package com.planitsquare.recruitment.core.configuration;

import com.planitsquare.recruitment.application.quartz.QuartzAutoSyncJob;
import java.util.TimeZone;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail autoSyncQuartzJob() {
        return JobBuilder.newJob(QuartzAutoSyncJob.class)
            .withIdentity("holidayAutoSync")
            .storeDurably()
            .build();
    }

    @Bean
    public Trigger autoSyncTrigger() {
        return TriggerBuilder.newTrigger()
            .forJob(autoSyncQuartzJob())
            .withIdentity("autoSyncTrigger")
            .withSchedule(CronScheduleBuilder
                .cronSchedule("0 0 1 2 1 ?")
                .inTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
                .withMisfireHandlingInstructionFireAndProceed())
            .build();
    }

}

