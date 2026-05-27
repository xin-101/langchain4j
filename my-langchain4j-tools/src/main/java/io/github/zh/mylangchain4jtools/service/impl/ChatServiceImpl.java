package io.github.zh.mylangchain4jtools.service.impl;

import io.github.zh.mylangchain4jtools.dto.*;
import io.github.zh.mylangchain4jtools.enums.IntentType;
import io.github.zh.mylangchain4jtools.service.ChatService;
import io.github.zh.mylangchain4jtools.service.LostAndFoundService;
import io.github.zh.mylangchain4jtools.service.MyAiAssistant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private MyAiAssistant myAiAssistant;
    @Resource
    private LostAndFoundService lostAndFoundService;

    @Override
    public Flux<String> chat(String userId, String message) {

        IntentRecognitionResult intentRecognitionResult = myAiAssistant.recognizeIntent(userId, message);

        IntentType intentType = intentRecognitionResult.getIntentType();
        switch (intentType) {
            case LOST_ITEM_REGISTER:
                LostItemRegisterResult lostItemRegisterResult=myAiAssistant.lostItemRegisterResult(userId, message);
                log.info("物品丢失登记：{}",lostItemRegisterResult);
                //保存到数据库
                if (lostItemRegisterResult.getCanCompleteRegistration()) {
                    log.info("物品丢失登记成功，开始保存到数据库");
                    Long lostItemId = lostAndFoundService.saveLostItem(userId, lostItemRegisterResult);
                    if (lostItemId != null) {
                        log.info("物品丢失登记成功，物品ID为：{}", lostItemId);
                    }
                }
                return Flux.just(lostItemRegisterResult.getAiFinalOutputSummary());
            case FOUND_ITEM_REGISTER:
                FoundItemRegisterResult foundItemRegisterResult=myAiAssistant.foundItemRegisterResult(userId, message);
                log.info("物品捡到登记：{}",foundItemRegisterResult);
                if (foundItemRegisterResult.getCanCompleteRegistration()) {
                    log.info("物品捡到登记成功，开始保存到数据库");
                    Long foundItemId = lostAndFoundService.saveFoundItem(userId, foundItemRegisterResult);
                    if (foundItemId != null) {
                        log.info("物品捡到登记成功，物品ID为：{}", foundItemId);
                    }
                }
                return Flux.just(foundItemRegisterResult.getAiFinalOutputSummary());
            case LOST_ITEM_QUERY:
                LostItemQueryResult lostItemQueryResult=myAiAssistant.lostItemQueryResult(userId, message);
                log.info("失物信息查询：{}",lostItemQueryResult);
                return Flux.just(lostItemQueryResult.getAiFinalOutputSummary());
            case PROCESS_CONSULTATION:
                ProcessConsultationResult processConsultationResult=myAiAssistant.processConsultationResult(userId, message);
                log.info("流程咨询：{}",processConsultationResult);
                return Flux.just(processConsultationResult.getAiFinalOutputSummary());
            case IRRELEVANT_TOPIC:
                return Flux.just("抱歉~我的工作仅限于失物招领");
        }
        return Flux.just(intentRecognitionResult.getSuggestedResponse());
    }
}
