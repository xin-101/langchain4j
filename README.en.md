# LangChain4j Intelligent Chat and Lost & Found System

An intelligent chat application built on the LangChain4j framework, integrating multiple large language models, supporting chat memory persistence, and providing a complete solution for lost and found functionality.

## Project Overview

This project is a full-stack intelligent application with a Spring Boot + LangChain4j backend and a WeChat Mini Program frontend. Key features include:

- **Intelligent Chat**: An AI-powered conversational system based on large language models
- **Chat Memory**: Context-aware memory with persistence to MySQL
- **Lost & Found**: Comprehensive functionality for registering and querying lost or found items
- **Intent Recognition**: Automatic identification of user intents (lost registration, found registration, query, consultation, etc.)
- **Streamed Responses**: Support for SSE streamed output

## Technology Stack

### Backend
- Java 17
- Spring Boot
- LangChain4j (Aliyun Bayan Qwen, OpenAI)
- MyBatis Plus
- MySQL
- WebFlux (streamed responses)

### Frontend
- WeChat Mini Program (uni-app)

## Module Description

### my-langchain4j (Core Example Module)

Basic usage examples of the LangChain4j framework, including:

- Integration of multiple AI models (OpenAI, Aliyun Qwen)
- Multi-model switching via Factory + Strategy pattern
- AI service usage via AiService annotations
- ChatMemory configuration
- MySQL persistence of chat history
- Integration of external Tools

### my-langchain4j-tools (Business Application Module)

A lost & found business system implemented using LangChain4j, including:

- **Intelligent Chat Service**: Intent recognition and intelligent responses via AI
- **Lost Item Management**: Registration and querying of lost items
- **Found Item Management**: Registration and querying of found items
- **Chat History Persistence**: Automatic recording of conversations via AOP
- **Streamed Responses**: Support for real-time streamed output

### my-tools (WeChat Mini Program Frontend)

The配套 WeChat Mini Program frontend providing user interaction interfaces.

## Quick Start

### Prerequisites

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- WeChat Developer Tools (to run the mini program)

### Configuration Steps

1. **Clone the Project**

```bash
git clone https://gitee.com/xin-study/langchain4j.git
```

2. **Configure the Database**

Execute the SQL scripts under the `sql/` directory to create database tables.

3. **Configure API Keys**

Copy the environment configuration file and set the required API keys:

```bash
cp my-langchain4j-tools/src/main/resources/.env-demo my-langchain4j-tools/src/main/resources/.env
```

Configure the following in the `.env` file:
- Aliyun Bayan API Key
- OpenAI API Key (if applicable)

4. **Build and Run**

```bash
cd my-langchain4j-tools
mvn spring-boot:run
```

### API Endpoints

#### AI Chat Endpoint

```
GET /ai/chat?userId=xxx&message=Hello
```

#### Streamed Chat Endpoint

```
GET /ai/chatStream?userId=xxx&message=Hello
```

#### Lost & Found Intent Recognition

```
GET /api/lost-and-found/recognize-intent?userId=xxx&message=I found something
```

## Project Structure

```
langchain4j/
├── my-langchain4j/                 # Core example module
│   ├── src/main/java/
│   │   └── io/github/zh/mylangchain4j/
│   │       ├── config/             # Configuration classes
│   │       ├── controller/         # Controllers
│   │       ├── factory/            # Factory pattern implementation
│   │       ├── mapper/             # Data access layer
│   │       ├── prompt/             # AI prompts
│   │       └── service/            # Service layer
│   └── src/main/resources/
│       ├── application.yml         # Configuration file
│       └── db/                     # Database scripts
│
├── my-langchain4j-tools/           # Business application module
│   ├── src/main/java/
│   │   └── io/github/zh/mylangchain4jtools/
│   │       ├── aop/                # AOP aspects
│   │       ├── config/             # Configuration classes
│   │       ├── controller/         # Controllers
│   │       ├── dto/                # Data transfer objects
│   │       ├── entity/             # Entity classes
│   │       ├── enums/              # Enum definitions
│   │       ├── mapper/             # Data access layer
│   │       ├── service/            # Service layer
│   │       └── tools/              # Utility classes
│   └── src/main/resources/
│       ├── application.yml
│       └── sql/                    # Database scripts
│
└── my-tools/                       # WeChat Mini Program frontend
    ├── pages/                      # Page components
    ├── utils/                      # Utility functions
    └── static/                     # Static resources
```

## Core Features Demo

### 1. Basic Chat

```java
// Using AiService interface
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel")
public interface Assistant {
    String chat(String message);
}
```

### 2. Chat with Memory

```java
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel", 
           chatMemoryProvider = "chatMemoryProvider")
public interface PersistentAssistant {
    String chat(String userId, String message);
}
```

### 3. Integration with External Tools

```java
@AiService(wiringMode = EXPLICIT, chatModel = "qwenChatModel",
           chatMemoryProvider = "chatMemoryProvider",
           tools = {"myTools", "loadMessageTools"})
public interface MyAiAssistant {
    String chat(String userId, String message);
}
```

## Intent Types

The system automatically recognizes the following user intents:

| Intent Type | Description |
|-------------|-------------|
| LOST_ITEM_REGISTER | Register a lost item |
| FOUND_ITEM_REGISTER | Register a found item |
| LOST_ITEM_QUERY | Query lost item information |
| PROCESS_CONSULTATION | Process consultation |
| IRRELEVANT_TOPIC | Irrelevant topic |

## License

This project is provided solely for learning and reference purposes.