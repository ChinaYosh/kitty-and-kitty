package com.animals.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChatHistoryRepository
{
    void save(String type, String chatId);
    List<String> getChatIds(String type);
}
