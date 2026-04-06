package com.animals.controller;

import com.animals.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
@RestController
@Slf4j
@RequestMapping("/ai")
@RequiredArgsConstructor
public class GameController
{
    private final ChatClient gameClient;

    @RequestMapping(value = "/game",produces = "text/html;charset=utf-8")
    public Flux<String> chat(String prompt, String chatId )
    {
        log.info("question:{}",prompt);
        //流式返回
        return gameClient
                .prompt(prompt)
                .advisors( a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }

}
