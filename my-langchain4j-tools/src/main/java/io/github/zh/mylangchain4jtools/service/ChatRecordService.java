package io.github.zh.mylangchain4jtools.service;

import io.github.zh.mylangchain4jtools.entity.ChatRecord;

import java.util.List;

public interface ChatRecordService {
    /**
     * 保存聊天记录
     */
    Long saveChatRecord(Long userId, String role, String content);

    /**
     * 根据用户ID查询聊天记录
     */
    List<ChatRecord> getChatRecordsByUserId(Long userId, Integer limit);

    /**
     * 根据用户ID和角色查询聊天记录
     */
    List<ChatRecord> getChatRecordsByUserIdAndRole(Long userId, String role, Integer limit);

    /**
     * 删除用户的聊天记录
     */
    boolean deleteChatRecordsByUserId(Long userId);

    /**
     * 批量保存聊天记录
     */
    boolean batchSaveChatRecords(List<ChatRecord> chatRecords);
}
