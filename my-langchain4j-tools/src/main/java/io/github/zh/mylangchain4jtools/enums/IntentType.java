package io.github.zh.mylangchain4jtools.enums;

/**
 * 用户意图枚举
 */
public enum IntentType {
    /**
     * 丢失信息登记
     */
    LOST_ITEM_REGISTER("丢失信息登记", "用户需要登记丢失的物品信息"),

    /**
     * 捡到物品登记
     */
    FOUND_ITEM_REGISTER("捡到物品登记", "用户需要登记捡到的物品信息"),

    /**
     * 失物信息查询
     */
    LOST_ITEM_QUERY("物品信息查询", "用户需要查询失物招领信息"),

    /**
     * 流程咨询
     */
    PROCESS_CONSULTATION("流程咨询", "用户咨询失物招领相关流程"),

    /**
     * 无关话题
     */
    IRRELEVANT_TOPIC("无关话题", "用户输入与失物招领无关的内容");

    private final String description;
    private final String detail;

    IntentType(String description, String detail) {
        this.description = description;
        this.detail = detail;
    }

}
