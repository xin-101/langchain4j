package io.github.zh.mylangchain4j.controller;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;
import io.github.zh.mylangchain4j.factory.ChatStrategyFactory;
import io.github.zh.mylangchain4j.factory.strategy.ChatStrategy;
import io.github.zh.mylangchain4j.prompt.DoctorAssistant;
import io.github.zh.mylangchain4j.prompt.DoctorPrompt;
import io.github.zh.mylangchain4j.service.Assistant;
import io.github.zh.mylangchain4j.service.MysqlAssistant;
import io.github.zh.mylangchain4j.service.PersistentAssistant;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/test")
public class AiChatController {

    @Resource
    private OpenAiChatModel model;

    @Operation(summary = "OpenAi测试接口")
    @GetMapping("/chat")
    public String chat(String question) {
        String answer = model.chat(question);
        return answer;
    }

    //测试的是 阿里云百炼平台API
    @Resource
    private QwenChatModel qwenChatModel;

    @Operation(summary = "阿里千问")
    @GetMapping("/qwenChat")
    public String qwenChat(String question) {
        String answer = qwenChatModel.chat(question);
        return answer;
    }

    //阿里万相集成
    @Operation(summary = "阿里万相")
    @GetMapping("/aliChat")
    public String aliChat(String question) {
        WanxImageModel wanxImageModel = WanxImageModel.builder()
                .modelName("wanx2.1-t2i-plus")
                .apiKey("sk-dd9606c6ee2a4bb0908fb47f0434f3ee")
                .build();
        Response<Image> response = wanxImageModel.generate(question);

        return response.content().url().toString();
    }

    //使用工厂+策略 获取模型
    @Operation(summary = "使用工厂+策略 获取模型")
    @GetMapping("/factoryChat")
    public String factoryChat(String question, Integer modelCode) {
        ChatStrategy chatStrategy = ChatStrategyFactory.getChatStrategy(modelCode);
        String answer = chatStrategy.chat(question);
        return answer;
    }

    //
    @Resource
    private QwenStreamingChatModel qwenStreamingChatModel;

    @Operation(summary = "阿里云百炼平台流式测试接口")
    @GetMapping(value = "/qwenStreamingChat", produces = "text/stream;charset=UTF-8")
    public Flux<String> qwenStreamingChat(String question) {
        Flux<Object> objectFlux = Flux.create(flux -> {
            qwenStreamingChatModel.chat(question, new StreamingChatResponseHandler() {

                @Override
                public void onPartialResponse(String s) {
                    flux.next(s);
                }

                @Override
                public void onCompleteResponse(ChatResponse chatResponse) {
                    flux.complete();
                }

                @Override
                public void onError(Throwable throwable) {
                    flux.error(throwable);
                }
            });
        });
        return objectFlux.cast(String.class);
    }

    //第一种使用AiService的方式
    @Operation(summary = "第一种使用AiService的方式")
    @GetMapping("/aiServiceTest1")
    public String aiServiceTest1(String question) {
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        return assistant.chat(question);
    }

    //第二种使用AiService的方式

    @Resource
    private Assistant assistant;

    @Operation(summary = "第二种使用AiService的方式")
    @GetMapping("/aiServiceTest2")
    public String aiServiceTest2(String question) {

        String chat = assistant.chat(question);
        return chat;
    }

    //AiService使用持久化会话-消息隔离

    @Resource
    private PersistentAssistant persistentAssistant;

    @Operation(summary = "AiService使用持久化会话-消息隔离")
    @GetMapping("/persistentAssistant")
    public String persistentAssistant(String userId, String question) {

        String chat = persistentAssistant.chat(userId, question);
        return chat;
    }

    //Mysql持久化消息
    @Resource
    private MysqlAssistant mysqlAssistant;

    @Operation(summary = "Mysql持久化消息")
    @GetMapping("/mysqlAssistant")
    public String mysqlAssistant(String userId, String question) {

        String chat = mysqlAssistant.chat(userId, question);
        return chat;
    }

    @Operation(summary = "Mysql持久化消息2")
    @GetMapping("/mysqlAssistant2")
    public String mysqlAssistant2(String userId, String age, String question) {

        String chat = mysqlAssistant.chat2(userId, age, question);
        return chat;
    }

    @Resource
    DoctorAssistant doctorAssistant;

    @Operation(summary = "医师助手")
    @GetMapping("/doctor")
    public String doctor(String book, String question) {
        String chat = doctorAssistant.chat(new DoctorPrompt(book, question));
        return chat;
    }

    //AiService使用外部Tools工具
    @Operation(summary = "AiService使用外部Tools工具")
    @GetMapping("/tools")
    public String tools(String userId, String question) {

        String chat = persistentAssistant.chat(userId, question);
        return chat;
    }

}
