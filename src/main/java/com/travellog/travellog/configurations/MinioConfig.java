package com.travellog.travellog.configurations;

import com.travellog.travellog.dtos.minio.MinioConfigurationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioConfig {
    @Autowired
    private MinioConfigurationDto minioConfiguration;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(minioConfiguration.getMinioEndpoint())
                .credentials(minioConfiguration.getMinioAccessKey(), minioConfiguration.getMinioSecretKey()).build();
    }
}
