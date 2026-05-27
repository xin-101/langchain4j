package io.github.zh.mylangchain4jtools.aop;

import io.github.zh.mylangchain4jtools.service.ChatRecordService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

@Aspect
@Component
@Slf4j
public class ChatRecordAspect {

    @Resource
    private ChatRecordService chatRecordService;
    @Autowired
    private View error;

    /**
     * 聊天记录切面
     * 在chat方法执行前记录用户信息，执行后记录AI消息
     */
    @Around("execution(* io.github.zh.mylangchain4jtools.service.impl.ChatServiceImpl.chat(..))")
    public Object recordChatMessages(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String userId = (String) args[0];
        String userMessage = (String) args[1];

        Long userIdLong = null;
        try {
            userIdLong = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            log.error("用户ID格式不正确：{}", userId);
        }

        // 记录用户信息（对话前）
        if (userIdLong != null) {
            chatRecordService.saveChatRecord(userIdLong, "user", userMessage);
            log.info("记录用户消息，用户ID：{}，消息：{}", userId, userMessage);
        }

        // 执行原方法
        Object result = joinPoint.proceed();

        // 如果返回值是Flux，需要在流中收集AI响应并记录
        if (result instanceof Flux) {
//            /unchecked/
            Flux<String> flux = (Flux<String>) result;
            AtomicReference<StringBuilder> aiMessage = new AtomicReference<>(new StringBuilder());
            Long finalUserIdLong = userIdLong;

            return flux.doOnNext(chunk -> {
                // 收集AI响应的每个片段
                aiMessage.get().append(chunk);
            }).doOnComplete(() -> {
                if (finalUserIdLong != null) {
                    String completeMessage = aiMessage.get().toString();
                    chatRecordService.saveChatRecord(finalUserIdLong, "ai", completeMessage);
                    log.info("记录AI消息，用户ID：{}，消息：{}", userId, completeMessage.length());
                }
            }).doOnError(error -> {
                log.error("处理聊天流时发生错误，用户ID：{}", userId, error);
            });
        }
        return result;
    }
}
