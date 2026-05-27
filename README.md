

# LangChain4j 智能聊天与失物招领系统

基于 LangChain4j 框架构建的智能聊天应用，集成了多种大语言模型，支持聊天记忆持久化，并提供失物招领功能的完整解决方案。

## 项目简介

本项目是一个前后端一体的智能化应用，后端采用 Spring Boot + LangChain4j 构建，前端为微信小程序。主要功能包括：

- **智能对话**：基于大语言模型的智能对话系统
- **聊天记忆**：支持上下文记忆，可持久化到 MySQL
- **失物招领**：完整的丢失物品和捡到物品登记与查询功能
- **意图识别**：自动识别用户意图（丢失登记、捡到登记、查询、咨询等）
- **流式响应**：支持 SSE 流式输出

## 技术栈

### 后端
- Java 17
- Spring Boot
- LangChain4j（阿里云百炼 Qwen、OpenAI）
- MyBatis Plus
- MySQL
- WebFlux（流式响应）

### 前端
- 微信小程序（uni-app）

## 模块说明

### my-langchain4j（核心示例模块）

LangChain4j 框架的基础使用示例，包含：

- 多种 AI 模型接入（OpenAI、阿里 Qwen）
- 工厂 + 策略模式实现多模型切换
- AiService 注解方式使用 AI 服务
- 聊天记忆（ChatMemory）配置
- MySQL 持久化聊天记录
- 外部 Tools 工具集成

### my-langchain4j-tools（业务应用模块）

基于 LangChain4j 实现的失物招领业务系统，包含：

- **智能对话服务**：通过 AI 进行意图识别和智能回复
- **失物管理**：丢失物品登记、查询
- **招领管理**：捡到物品登记、查询
- **聊天记录持久化**：使用 AOP 自动记录对话
- **流式响应**：支持实时流式输出

### my-tools（微信小程序前端）

配套的微信小程序前端，提供用户交互界面。

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- 微信开发者工具（运行小程序）

### 配置步骤

1. **克隆项目**

```bash
git clone https://gitee.com/xin-study/langchain4j.git
```

2. **配置数据库**

执行 `sql/` 目录下的 SQL 脚本创建数据库表。

3. **配置 API Key**

复制环境配置文件并配置必要的 API Key：

```bash
cp my-langchain4j-tools/src/main/resources/.env-demo my-langchain4j-tools/src/main/resources/.env
```

在 `.env` 文件中配置：
- 阿里云百炼 API Key
- OpenAI API Key（如使用）

4. **编译运行**

```bash
cd my-langchain4j-tools
mvn spring-boot:run
```

### 接口说明

#### AI 聊天接口

```
GET /ai/chat?userId=xxx&message=你好
```

#### 流式聊天接口

```
GET /ai/chatStream?userId=xxx&message=你好
```

#### 失物招领意图识别

```
GET /api/lost-and-found/recognize-intent?userId=xxx&message=我捡到了东西
```

## 项目结构

```
langchain4j/
├── my-langchain4j/                 # 核心示例模块
│   ├── src/main/java/
│   │   └── io/github/zh/mylangchain4j/
│   │       ├── config/             # 配置类
│   │       ├── controller/         # 控制器
│   │       ├── factory/            # 工厂模式实现
│   │       ├── mapper/             # 数据访问层
│   │       ├── prompt/             # AI 提示词
│   │       └── service/            # 服务层
│   └── src/main/resources/
│       ├── application.yml         # 配置文件
│       └── db/                     # 数据库脚本
│
├── my-langchain4j-tools/           # 业务应用模块
│   ├── src/main/java/
│   │   └── io/github/zh/mylangchain4jtools/
│   │       ├── aop/                # AOP 切面
│   │       ├── config/             # 配置类
│   │       ├── controller/         # 控制器
│   │       ├── dto/                # 数据传输对象
│   │       ├── entity/             # 实体类
│   │       ├── enums/              # 枚举定义
│   │       ├── mapper/             # 数据访问层
│   │       ├── service/            # 服务层
│   │       └── tools/              # 工具类
│   └── src/main/resources/
│       ├── application.yml
│       └── sql/                    # 数据库脚本
│
└── my-tools/                       # 微信小程序前端
    ├── pages/                      # 页面组件
    ├── utils/                      # 工具函数
    └── static/                     # 静态资源
```

## 核心功能演示

### 1. 基础对话

```java
// 使用 AiService 接口
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel")
public interface Assistant {
    String chat(String message);
}
```

### 2. 带记忆的对话

```java
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel", 
           chatMemoryProvider = "chatMemoryProvider")
public interface PersistentAssistant {
    String chat(String userId, String message);
}
```

### 3. 集成外部工具

```java
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel",
           chatMemoryProvider = "chatMemoryProvider",
           tools = {"myTools", "loadMessageTools"})
public interface MyAiAssistant {
    String chat(String userId, String message);
}
```

## 意图类型

系统支持自动识别以下用户意图：

| 意图类型 | 说明 |
|---------|------|
| LOST_ITEM_REGISTER | 丢失物品登记 |
| FOUND_ITEM_REGISTER | 捡到物品登记 |
| LOST_ITEM_QUERY | 失物信息查询 |
| PROCESS_CONSULTATION | 流程咨询 |
| IRRELEVANT_TOPIC | 无关话题 |

## 许可证

本项目仅供学习参考使用。