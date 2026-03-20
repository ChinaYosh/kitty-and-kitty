package com.animals.config;


import com.animals.properties.WeAPIsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Configuration
public  class WebAIConfig
{
    @Bean
    public RestTemplate restTemplate()
    {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);
        return new RestTemplate(factory);
    }
    @Bean
    public String weapisFullUrl(WeAPIsProperties properties)
    {

        String baseUrl = properties.getBaseUrl().endsWith("/")
                ? properties.getBaseUrl().substring(0, properties.getBaseUrl().length() - 1)
                : properties.getBaseUrl();
        String chatPath = properties.getChatPath().startsWith("/")
                ? properties.getChatPath()
                : "/" + properties.getChatPath();
        return baseUrl + chatPath;
    }
}