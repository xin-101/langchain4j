package io.github.zh.mylangchain4j.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(wiringMode = EXPLICIT,chatModel = "qwenChatModel",chatMemory = "chatMemory")
//@AiService(wiringMode = EXPLICIT,chatModel = "qwenChatModel")
public interface Assistant {

    @SystemMessage(fromResource = "message/SystemMessage.txt")
    String chat(String userMessage);
}