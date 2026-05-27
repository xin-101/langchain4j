CREATE TABLE IF NOT EXISTS chat_message (
                                            id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '消息ID',
                                            user_id VARCHAR(255) NOT NULL COMMENT '用户ID',
    content LONGTEXT NOT NULL COMMENT '消息内容',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_session_id (user_id),
    INDEX idx_created_at (created_at)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='聊天消息表';