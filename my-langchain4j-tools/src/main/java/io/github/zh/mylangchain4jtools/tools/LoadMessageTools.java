package io.github.zh.mylangchain4jtools.tools;

import dev.langchain4j.agent.tool.Tool;
import io.github.zh.mylangchain4jtools.entity.ChatRecord;
import io.github.zh.mylangchain4jtools.service.ChatRecordService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class LoadMessageTools {

    @Resource
    private ChatRecordService chatRecordService;

    @Tool("加载用户与AI的历史聊天记录")
    public String loadChatHistory(String userId) {
        log.info("加载用户与AI的历史聊天记录，用户ID：{}", userId);

        List<ChatRecord> chatRecordsByUserId = chatRecordService.getChatRecordsByUserId(Long.parseLong(userId), null);
        StringBuilder sb = new StringBuilder();
        if (chatRecordsByUserId != null && !chatRecordsByUserId.isEmpty()) {
            for (ChatRecord chatRecord : chatRecordsByUserId) {
                String role = chatRecord.getRole();
                if ("user".equalsIgnoreCase(role)) {
                    sb.append("用户：").append(chatRecord.getContent()).append("\n");
                } else if ("assistant".equalsIgnoreCase(role)) {
                    sb.append("AI：").append(chatRecord.getContent()).append("\n");
                }
            }
            return sb.toString();
        }
        return sb.toString();
    }
}
