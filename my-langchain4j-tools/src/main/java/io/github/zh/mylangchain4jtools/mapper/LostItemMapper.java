package io.github.zh.mylangchain4jtools.mapper;

import io.github.zh.mylangchain4jtools.entity.FoundItem;
import io.github.zh.mylangchain4jtools.entity.LostItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LostItemMapper {

    /**
     * 插入丢失物品记录
     */
    int insert(LostItem lostItem);

    /**
     * 根据ID查询
     */
    LostItem selectById(Long id);

    /**
     * 根据用户ID查询
     */
    List<LostItem> selectByUserId(String userId);

    /**
     * 根据关键词搜索
     */
    List<LostItem> searchByKeyword(@Param("keyword") String keyword, @Param("category") String category, @Param("location") String location);

    /**
     * 更新状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新记录
     */
    int update(LostItem lostItem);
}
