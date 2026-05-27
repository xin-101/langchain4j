package io.github.zh.mylangchain4jtools.dto;

import dev.langchain4j.model.output.structured.Description;
import io.github.zh.mylangchain4jtools.enums.IntentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 意图识别结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntentRecognitionResult {
    /**
     * 识别的意图类型
     */
    @Description("""
            识别的意图类型
            LOST_ITEM_REGISTER:丢失信息登记
            FOUND_ITEM_REGISTER:捡到物品登记
            LOST_ITEM_QUERY:失物信息查询
            PROCESS_CONSULTATION:流程咨询
            IRRELEVANT_TOPIC:无关话题
            """)
    private IntentType intentType;

    /**
     * 建议的回复内容
     */
    @Description("针对该意图建议的回复内容")
    private String suggestedResponse;

    /**
     * 下一步建议操作
     */
    @Description("针对用户下一步应该做什么")
    private String nextAction;
}
