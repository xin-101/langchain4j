-- 失物招领数据库表结构

CREATE DATABASE IF NOT EXISTS langchain4j DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE langchain4j;

-- 丢失物品登记表
CREATE TABLE lost_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id VARCHAR(100) NOT NULL COMMENT '用户ID',
    item_name VARCHAR(200) NOT NULL COMMENT '物品名称',
    item_description TEXT COMMENT '物品详细描述',
    category VARCHAR(100) COMMENT '物品类别',
    lost_location VARCHAR(500) COMMENT '丢失地点',
    lost_time VARCHAR(200) COMMENT '丢失时间',
    contact_info VARCHAR(200) COMMENT '联系方式',
    contact_name VARCHAR(100) COMMENT '联系人姓名',
    remarks TEXT COMMENT '备注信息',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待处理, 1-已找到, 2-已关闭',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX idx_user_id (user_id),
    INDEX idx_category (category),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='丢失物品登记表';

-- 拾到物品登记表
CREATE TABLE found_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id VARCHAR(100) NOT NULL COMMENT '用户ID',
    item_name VARCHAR(200) NOT NULL COMMENT '物品名称',
    item_description TEXT COMMENT '物品详细描述',
    category VARCHAR(100) COMMENT '物品类别',
    found_location VARCHAR(500) COMMENT '捡到地点',
    found_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '捡到时间',
    contact_info VARCHAR(200) COMMENT '联系方式',
    contact_name VARCHAR(100) COMMENT '联系人姓名',
    remarks TEXT COMMENT '备注信息',
    status TINYINT DEFAULT 0 COMMENT '状态: 0-待认领, 1-已认领, 2-已关闭',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    INDEX idx_user_id (user_id),
    INDEX idx_category (category),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='拾到物品登记表';