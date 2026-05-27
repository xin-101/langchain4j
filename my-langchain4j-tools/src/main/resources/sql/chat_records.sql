CREATE TABLE `chat_records`
(
    `id`         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id`    BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `role`       VARCHAR(20) NOT NULL COMMENT '角色(user/ai)',
    `content`    TEXT        NOT NULL COMMENT '聊天内容',
    `created_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX        `idx_user_id` (`user_id`),
    INDEX        `idx_created_at` (`created_at`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '聊天记录表';