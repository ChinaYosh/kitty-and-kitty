package com.animals.config;

import com.animals.constants.SystemConstants;
import com.animals.tools.CourseTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.VectorStoreRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import org.springframework.ai.document.Document;
import java.util.List;

import static org.yaml.snakeyaml.nodes.Tag.STR;


@Component
@Slf4j
public class CommonConfiguration
{

    @Bean
    public ChatMemory chatMemory()
    {

        return MessageWindowChatMemory.builder()
                // 内存存储仓库
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                // 保留最近 10 轮对话，防止上下文过长
                .maxMessages(10)
                .build();

    }
    @Bean
    public ChatClient chatClient(OpenAiChatModel ollamaChatModel, ChatMemory chatMemory)
    {
        return ChatClient.builder(ollamaChatModel)
                .defaultOptions(ChatOptions.builder().model("qwen-omni-turbo").build())
                .defaultSystem(SystemConstants.getChatPrompt())
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .build()
                )
                .build();
    }
    @Bean
    public ChatClient gameClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory)
    {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(SystemConstants.getGamePrompt())
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .build()
                )
                .build();
    }
    @Bean
    public ChatClient serviceClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory, CourseTools courseTools)
    {
        return ChatClient.builder(openAiChatModel)
                .defaultSystem(SystemConstants.getServicePrompt())
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .build()
                )
                .defaultTools(courseTools)
                .build();
    }
    @Bean
    public VectorStore vectorStore(OpenAiEmbeddingModel openAiEmbeddingModel)
    {
        return SimpleVectorStore.builder(openAiEmbeddingModel).build();
    }
    @Bean
    public ChatClient pdfChatClient(
            OpenAiChatModel model,
            ChatMemory chatMemory,
            VectorStore vectorStore) {

        // 1. 定义基础搜索参数
        SearchRequest searchRequest = SearchRequest.builder()
                .similarityThreshold(0.5)
                .topK(2)
                .build();

        return ChatClient.builder(model)
                .defaultSystem("""
                你是一个专业的PDF文档问答助手。
                请**严格根据提供的上下文资料**回答用户问题...
                """)
                .defaultAdvisors(
                        (Advisor) MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        (Advisor) new SimpleLoggerAdvisor(),

                        // 2. 使用 Lambda 表达式直接实现 VectorStoreRetriever 接口
                        (Advisor) RetrievalAugmentationAdvisor.builder()
                                .documentRetriever(query ->
                                        vectorStore.similaritySearch(
                                                SearchRequest.from(searchRequest)
                                                        .query(String.valueOf(query)) // 这里的 query 是 Advisor 传进来的用户当前问题
                                                        .build()
                                        )
                                )
                                .build()
                )
                .build();
    }

}
