package com.animals.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryChatHistoryRepository implements  ChatHistoryRepository{
    Map<String,List<String>> chatHistory = new HashMap<>();
    @Override
    public void save(String type, String chatId) {
        List<String> chatIds = chatHistory.computeIfAbsent(type ,k -> new ArrayList<>());
        if(chatIds.contains(chatId) == false)
        {
            chatIds.add(chatId);
        }
        log.info("save,{},{}",type,chatId);
    }

    @Override
    public List<String> getChatIds(String type) {

        log.info("getChatIds,{}",type);
         return chatHistory.getOrDefault(type,List.of());
    }
}
