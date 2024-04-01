package com.batch.config;

import com.batch.steps.ItemReaderStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    @Bean
    public ItemReaderStep itemReaderStep() {
        return new ItemReaderStep();
    }
}
