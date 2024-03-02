package com.travellog.travellog.dtos.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfigurationDto {
    private String minioEndpoint;
    private String minioBucket;
    private String minioAccessKey;
    private String minioSecretKey;

}
