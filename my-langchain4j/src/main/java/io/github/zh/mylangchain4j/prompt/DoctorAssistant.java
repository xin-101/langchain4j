package io.github.zh.mylangchain4j.prompt;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(wiringMode = EXPLICIT,chatModel = "qwenChatModel",chatMemoryProvider = "chatMysqlProvider")
public interface DoctorAssistant {

    @SystemMessage("你作为一个专业的医师，只可以回答关于病情相关的知识内容！" +
            "[病情解答]：简明扼要的说明大致情况，根据医书及自身判断出具报告，给出对应药方" +
            "[其他问题]：不属于医学范畴 一概拒绝回答")
     String chat(DoctorPrompt doctorPrompt);

}
