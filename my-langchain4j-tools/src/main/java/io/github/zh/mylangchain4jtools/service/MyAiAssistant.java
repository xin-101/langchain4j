package io.github.zh.mylangchain4jtools.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import io.github.zh.mylangchain4jtools.dto.*;
import reactor.core.publisher.Flux;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,
        chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamChatModel",
        chatMemoryProvider = "chatMemoryProvider",
        tools = {"myTools",  "loadMessageTools"}
)
public interface MyAiAssistant {

    String chat(@MemoryId String userId, @UserMessage String message);

    Flux<String> chatStream(@MemoryId String userId, @UserMessage String message);

    @SystemMessage(fromResource = "message/system.txt")
    @UserMessage("""
            用户ID为{{userId}},用户发送的消息为{{message}}
            """)
    IntentRecognitionResult recognizeIntent(@V("userId") String userId, @V("message") String message);

    @SystemMessage(fromResource = "message/system.txt")
    @UserMessage("""
            用户ID为{{userId}},用户发送的消息为{{message}}
            """)
    LostItemRegisterResult lostItemRegisterResult(@V("userId") String userId, @V("message") String message);

    @SystemMessage(fromResource = "message/system.txt")
    @UserMessage("""
            用户ID为{{userId}},用户发送的消息为{{message}}
            """)
    FoundItemRegisterResult foundItemRegisterResult(@V("userId") String userId, @V("message") String message);

    @SystemMessage(fromResource = "message/system.txt")
    @UserMessage("""
            用户ID为{{userId}},用户发送的消息为{{message}}
            """)
    LostItemQueryResult lostItemQueryResult(@V("userId") String userId, @V("message") String message);

    @SystemMessage(fromResource = "message/system.txt")
    @UserMessage("""
            用户ID为{{userId}},用户发送的消息为{{message}}
            """)
    ProcessConsultationResult processConsultationResult(@V("userId") String userId, @V("message") String message);

}
