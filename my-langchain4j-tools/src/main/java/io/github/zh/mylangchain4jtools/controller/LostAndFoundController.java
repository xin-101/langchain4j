package io.github.zh.mylangchain4jtools.controller;

import io.github.zh.mylangchain4jtools.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/lost-and-found")
@CrossOrigin(origins = "*") //允许跨域
public class LostAndFoundController {

    @Resource
    private ChatService chatService;

    @GetMapping(value = "/recognize-intent", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public Flux<String> recognizeIntent(
            @RequestParam(required = false, defaultValue = "anonymous") String userId,
            @RequestParam(required = false, defaultValue = "hello") String message) {
        System.out.println("收到请求 - userId = " + userId + ", message = " + message);
        try {
            Flux<String> result = chatService.chat(userId, message);
            System.out.println("chatService调用成功");
            return result;
        } catch (Exception e) {
            System.out.println("chatService调用失败" + e.getMessage());
            e.printStackTrace();
            return Flux.just("抱歉~服务暂不可用" + e.getMessage());
        }

    }

}
