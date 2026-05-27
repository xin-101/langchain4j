package io.github.zh.mylangchain4jtools.mapper;

import io.github.zh.mylangchain4jtools.entity.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天记录Mapper
 */
@Mapper
public interface ChatRecordMapper {
    /**
     * 聊天记录
     */
    int insert(ChatRecord chatRecord);
    /**
     * 根据ID查询
     */
    ChatRecord selectById(Long Id);
    /**
     * 根据用户ID查询聊天记录
     */
    List<ChatRecord> selectByUserId(@Param("userId") Long userId,
                                    @Param("limit") Integer limit);
    /**
     * 根据用户ID和角色查询聊天记录
     */
    List<ChatRecord> selectByUserIdAndRole(@Param("userId") Long userId,
                                           @Param("role") String role,
                                           @Param("limit") Integer limit);
    /**
     * 删除用户的聊天记录
     */
    int deleteByUserId(Long userId);
    /**
     * 批量插入聊天记录
     */
    int batchInsert(List<ChatRecord> chatRecords);
}
