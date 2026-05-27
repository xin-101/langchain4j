package io.github.zh.mylangchain4j.factory;

import io.github.zh.mylangchain4j.factory.strategy.ChatStrategy;
import io.github.zh.mylangchain4j.factory.strategy.impl.OpenAiChatStrategy;
import io.github.zh.mylangchain4j.factory.strategy.impl.QWenChatStrategy;

public class ChatStrategyFactory {

    //获取聊天大模型策略
    public static ChatStrategy getChatStrategy(Integer modelCode) {

        ChatModelEnum modelEnum=ChatModelEnum.getByCode(modelCode);

        switch (modelEnum){
            case OPEN_AI:
                return new OpenAiChatStrategy();
            case QWEN_CHAT:
                return new QWenChatStrategy();
            default:
                return new OpenAiChatStrategy();
        }
    }
}
