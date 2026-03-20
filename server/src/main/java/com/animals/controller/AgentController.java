package com.animals.controller;

import com.animals.dto.AIDOT;
import com.animals.properties.WeAPIsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import  com.animals.common.Result.Result;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agent")
@Slf4j
public class AgentController
{
    @Autowired
    private WeAPIsProperties weAPIsProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private String weapisFullUrl;

    @PostMapping("/talk")
    public Result talkWithAI(@RequestBody AIDOT aidot) {
        String msg = aidot.getMessage();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", weAPIsProperties.getModel());
        requestBody.put("messages", List.of(Map.of("role", "user", "content", msg)));
        requestBody.put("temperature", weAPIsProperties.getTemperature());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + weAPIsProperties.getApiKey());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        Map<String, Object> response = restTemplate.postForObject(weapisFullUrl, requestEntity, Map.class);
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        String aiReply = (String) message.get("content");
        log.info("Agent aiReply ,{}",aiReply);
        return Result.success(aiReply);
    }
}
