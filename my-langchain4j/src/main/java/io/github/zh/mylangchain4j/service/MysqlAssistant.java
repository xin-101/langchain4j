package io.github.zh.mylangchain4j.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(wiringMode = EXPLICIT,chatModel = "qwenChatModel",chatMemoryProvider = "chatMysqlProvider")
public interface MysqlAssistant {

    //@SystemMessage 是一个系统级别的提示词，会作为会话的开始 - 当我们调用这次chat对话的时候 系统为我们自动添加这个提示词
    //@UserMessage 是一个用户级别的提示词，会作为对话的开始 -
    //两者相比而言 - SystemMessage 的颗粒度会大一点 - UserMessage 的颗粒度会小一点


    @SystemMessage(fromResource = "message/SystemMessage.txt")
//    @UserMessage(fromResource = "message/UserMessage.txt")
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);

    @UserMessage(fromResource = "message/UserMessage2.txt")
    String chat2(@MemoryId String memoryId, @V("age") String age,@V("question") String question);
}
