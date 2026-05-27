package io.github.zh.mylangchain4j.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import io.github.zh.mylangchain4j.service.MysqlChatMemoryStore;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(10).build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return MemoryId -> MessageWindowChatMemory.builder()
                .id(MemoryId)
                .maxMessages(10)
                .build();
    }

    @Resource
    MysqlChatMemoryStore mysqlChatMemoryStore;

    @Bean
    public ChatMemoryProvider chatMysqlProvider() {
        return MemoryId -> MessageWindowChatMemory.builder()
                .id(MemoryId)
                .maxMessages(10)
                .chatMemoryStore(mysqlChatMemoryStore)
                .build();
    }
}
