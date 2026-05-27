package io.github.zh.mylangchain4jtools.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 聊天记录实体
 */
@Data
public class ChatRecord {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色（user/ai）
     */
    private String role;
    /**
     * 聊天内容
     */
    private String content;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
