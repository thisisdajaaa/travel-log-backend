package com.travellog.travellog.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfiguration {
    private String minioEndpoint;
    private String minioBucket;
    private String minioAccessKey;
    private String minioSecretKey;

}
