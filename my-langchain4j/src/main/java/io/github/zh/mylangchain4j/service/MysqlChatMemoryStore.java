package io.github.zh.mylangchain4j.service;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import io.github.zh.mylangchain4j.mapper.ChatMessageMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;

@Component
public class MysqlChatMemoryStore implements ChatMemoryStore {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        io.github.zh.mylangchain4j.entity.ChatMessage messages = chatMessageMapper.selectOneByUserId((String) memoryId);
        if (messages != null) {
            return messagesFromJson(messages.getContent());
        } else {
            return List.of();
        }
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        io.github.zh.mylangchain4j.entity.ChatMessage chatMessage = chatMessageMapper.selectOneByUserId((String) memoryId);
        if (chatMessage != null) {
            chatMessage.setContent(messagesToJson(messages));
            chatMessageMapper.update(chatMessage);
        } else {
            io.github.zh.mylangchain4j.entity.ChatMessage newChatMessage = new io.github.zh.mylangchain4j.entity.ChatMessage();
            newChatMessage.setUserId(memoryId.toString());
            newChatMessage.setContent(messagesToJson(messages));
            chatMessageMapper.insert(newChatMessage);
        }
    }

    @Override
    public void deleteMessages(Object memoryId){
        chatMessageMapper.deleteByUserId(memoryId.toString());
    }
}

