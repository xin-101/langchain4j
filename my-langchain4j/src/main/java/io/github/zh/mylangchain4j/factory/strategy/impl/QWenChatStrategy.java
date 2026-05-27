package io.github.zh.mylangchain4j.factory.strategy.impl;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import io.github.zh.mylangchain4j.factory.strategy.ChatStrategy;

public class QWenChatStrategy implements ChatStrategy {

    private QwenChatModel model;

    public QWenChatStrategy() {
        model=QwenChatModel.builder()
                .baseUrl("https://dashscope.aliyuncs.com/api/v1")
                .apiKey("sk-dd9606c6ee2a4bb0908fb47f0434f3ee")
                .modelName("qwen-plus")
                .build();
    }

    @Override
    public String chat(String question) {
        return model.chat(question);
    }
}
