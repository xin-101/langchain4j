package io.github.zh.mylangchain4j.factory.strategy.impl;

import dev.langchain4j.http.client.spring.restclient.SpringRestClient;
import dev.langchain4j.model.openai.OpenAiChatModel;
import io.github.zh.mylangchain4j.factory.strategy.ChatStrategy;

public class OpenAiChatStrategy implements ChatStrategy {

    private final OpenAiChatModel model;

    public OpenAiChatStrategy() {
        this.model= OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .httpClientBuilder(SpringRestClient.builder())
                .build();
    }

    @Override
    public String chat(String question) {
        return model.chat(question);
    }
}
