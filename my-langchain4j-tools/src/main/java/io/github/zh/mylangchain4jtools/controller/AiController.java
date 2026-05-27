package io.github.zh.mylangchain4jtools.controller;

import io.github.zh.mylangchain4jtools.service.MyAiAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Autowired
    private MyAiAssistant myAiAssistant;

    @GetMapping("/chat")
    public String chat(@RequestParam String userId,@RequestParam String message) {
        return myAiAssistant.chat(userId, message);
    }

    @GetMapping(value = "/chatStream",produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public Flux<String> chatStream(@RequestParam String userId,@RequestParam String message) {
        return myAiAssistant.chatStream(userId, message);
    }
}
