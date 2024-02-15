package com.travellog.travellog.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConversionConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public <S, T> T convert(S source, Class<T> targetClass) {
        return this.modelMapper().map(source, targetClass);
    }
}
