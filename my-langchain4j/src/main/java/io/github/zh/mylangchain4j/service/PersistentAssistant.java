package io.github.zh.mylangchain4j.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel", chatMemoryProvider = "chatMemoryProvider")
public interface PersistentAssistant {

    //@SystemMessage 是一个系统级别的提示词，会作为会话的开始 - 当我们调用这次chat对话的时候 系统为我们自动添加这个提示词
    @SystemMessage(fromResource = "message/SystemMessage.txt")
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);
}
