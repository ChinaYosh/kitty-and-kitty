package com.animals.controller;

import com.animals.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.content.Media;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/ai")
@RequiredArgsConstructor
public class ChatController
{

    private final  ChatClient chatClient;
    private final ChatHistoryRepository    chatHistoryRepository;

    @RequestMapping(value = "/chat",produces = "text/html;charset=utf-8")
    public Flux<String> chat(@RequestParam(value = "prompt")String prompt,@RequestParam(value = "chatId") String chatId,@RequestParam(value = "files",required = false) List<MultipartFile> files)
    {
        log.info("question:{}",prompt);
        chatHistoryRepository.save("chat",chatId);
        //流式返回
        if(files == null || files.isEmpty())
        {
            return chatClient
                .prompt(prompt)
                .advisors( a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();

        }
        return mutilModalChat(prompt,chatId,files);

    }

    private Flux<String>  mutilModalChat(String prompt, String chatId, List<MultipartFile> files)
    {
        List<Media> list = files.stream().map(
                file -> new Media
                        (
                         MimeType.valueOf(file.getContentType()), file.getResource()
                         )).toList();

        return chatClient
                .prompt()
                .user(p -> p.text(prompt).media(list.toArray(Media[]::new)))
                .advisors( a -> a.param(ChatMemory.CONVERSATION_ID, chatId))
                .stream()
                .content();
    }
}
