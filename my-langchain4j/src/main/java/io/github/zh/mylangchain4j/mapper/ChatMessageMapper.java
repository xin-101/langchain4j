package io.github.zh.mylangchain4j.mapper;

import io.github.zh.mylangchain4j.entity.ChatMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMessageMapper {

    @Insert("INSERT INTO chat_message (user_id,content) VALUES (#{userId},#{content})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(ChatMessage message);

    @Select("SELECT * FROM chat_message WHERE id = #{id}")
    ChatMessage selectById(Long id);

    @Select("SELECT * FROM chat_message WHERE user_id = #{userId}")
    ChatMessage selectOneByUserId(String userId);

    @Select("SELECT * FROM chat_message WHERE user_id = #{userId} ORDER BY created_at ASC")
    List<ChatMessage> selectByUserId(String userId);

    @Update("UPDATE chat_message SET content = #{content} WHERE id = #{id}")
    int update(ChatMessage message);

    @Delete("DELETE FROM chat_message WHERE id = #{id}")
    int deleteById(Long id);

    @Delete("DELETE FROM chat_message WHERE user_id = #{userId}")
    int deleteByUserId(String userId);



}
