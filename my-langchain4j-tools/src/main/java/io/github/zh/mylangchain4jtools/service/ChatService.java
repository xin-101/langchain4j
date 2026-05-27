package io.github.zh.mylangchain4jtools.service;

import reactor.core.publisher.Flux;

public interface ChatService {

    Flux<String> chat(String userId, String message);

}
