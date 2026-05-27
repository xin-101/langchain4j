package io.github.zh.mylangchain4jtools.service.impl;

import io.github.zh.mylangchain4jtools.entity.ChatRecord;
import io.github.zh.mylangchain4jtools.mapper.ChatRecordMapper;
import io.github.zh.mylangchain4jtools.service.ChatRecordService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ChatRecordServiceImpl implements ChatRecordService {
    @Resource
    private ChatRecordMapper chatRecordMapper;

    @Override
    public Long saveChatRecord(Long userId, String role, String content) {
        try {
            ChatRecord chatRecord = new ChatRecord();
            chatRecord.setUserId(userId);
            chatRecord.setRole(role);
            chatRecord.setContent(content);
            chatRecord.setCreatedAt(LocalDateTime.now());
            chatRecord.setUpdatedAt(LocalDateTime.now());

            int result = chatRecordMapper.insert(chatRecord);
            if (result > 0) {
                log.info("保存聊天记录成功，ID：{}，角色{}，记录{}", userId, role, chatRecord.getId());
                return chatRecord.getId();
            }
        } catch (Exception e) {
            log.error("保存聊天记录失败，用户ID：{}，角色：{}", userId, role);
        }
        return null;
    }

    @Override
    public List<ChatRecord> getChatRecordsByUserId(Long userId, Integer limit) {
        try {
            return chatRecordMapper.selectByUserId(userId, limit);
        } catch (Exception e) {
            log.error("查询用户聊天记录失败，用户ID：{}", userId, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<ChatRecord> getChatRecordsByUserIdAndRole(Long userId, String role, Integer limit) {
        try {
            return chatRecordMapper.selectByUserIdAndRole(userId, role, limit);
        } catch (Exception e) {
            log.error("查询用户聊天记录失败，用户ID：{}，角色：{}", userId,role, e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean deleteChatRecordsByUserId(Long userId) {
        try {
            int result = chatRecordMapper.deleteByUserId(userId);
            log.info("删除用户聊天记录成功，用户ID：{}，删除数量：{}", userId, result);
            return result > 0;

        } catch (Exception e) {
            log.error("删除用户聊天记录失败，用户ID：{}", userId, e);
            return false;
        }
    }

    @Override
    public boolean batchSaveChatRecords(List<ChatRecord> chatRecords) {
        if (chatRecords == null || chatRecords.isEmpty()) {
            return false;
        }
        try {
            LocalDateTime now = LocalDateTime.now();
            chatRecords.forEach(record -> {
                if (record.getCreatedAt() == null) {
                    record.setCreatedAt(now);
                }
                if (record.getUpdatedAt() == null) {
                    record.setUpdatedAt(now);
                }
            });
            int result = chatRecordMapper.batchInsert(chatRecords);
            log.info("批量保存聊天记录成功，数量：{}", result);
            return result > 0;
        } catch (Exception e) {
            log.error("批量保存聊天记录失败", e);
            return false;
        }
    }
}
