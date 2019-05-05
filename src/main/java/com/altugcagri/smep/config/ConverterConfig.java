package com.altugcagri.smep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.GenericConversionService;

@Configuration
public class ConverterConfig {

    @Bean
    public ConfigurableConversionService smepConversionService() {
        final ConfigurableConversionService conversionService = new GenericConversionService();

        return conversionService;
    }
}
