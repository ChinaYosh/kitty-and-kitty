package com.animals.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("weapis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeAPIsProperties
{
    private String apiKey;
    private String baseUrl;
    private String chatPath;
    private String model;
    private Double  temperature;
}
