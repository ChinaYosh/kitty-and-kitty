package com.animals.controller;

import com.animals.vo.MessageVO;
import com.animals.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai/history")
@RequiredArgsConstructor
@Slf4j
public class ChatHistoryController {
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatMemory chatMemory;
    @GetMapping("/{type}")
    public List<String> getChatIds(@PathVariable("type") String type)
    {
        log.info("getChatIds,{}",type);
        List <String> chatIds = chatHistoryRepository.getChatIds(type);
        log.info("chatIds,{}",chatIds);
        return chatIds;
    }

    @GetMapping("/{type}/{chatId}")
    public List<MessageVO> getChatId(@PathVariable("type") String type,
                            @PathVariable("chatId") String chatId)
    {
        log.info("getChatId,{},{}",type,chatId);
        //如果为空就新建一个list
        List<Message> messages = chatMemory.get(chatId);
        if(messages  == null)
        {
            messages = List.of();
        }

        return messages.stream().map(MessageVO::new).toList();
    }
}
