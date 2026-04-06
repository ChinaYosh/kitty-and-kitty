package com.animals.controller;


import com.animals.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2026-04-05
 */
@RestController
@Slf4j
@RequestMapping("/ai")
@RequiredArgsConstructor
public class CourseReservationController {
    private final ChatClient serviceClient;
    private final ChatHistoryRepository chatHistoryRepository;

    @RequestMapping(value = "/service",produces = "text/html;charset=utf-8")
    public Flux<String> chat(String prompt, String chatId )
    {
        log.info("question:{}",prompt);
        chatHistoryRepository.save("service",chatId);
        //流式返回
        return serviceClient
                .prompt(prompt)
                .advisors( a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }
}
