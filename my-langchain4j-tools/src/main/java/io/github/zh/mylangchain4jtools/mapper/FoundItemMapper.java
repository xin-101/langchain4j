package io.github.zh.mylangchain4jtools.mapper;

import io.github.zh.mylangchain4jtools.entity.FoundItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoundItemMapper {

    /**
     * 插入拾到物品记录
     */
    int insert(FoundItem foundItem);
    /**
     * 根据ID查询
     */
    FoundItem selectById(Long id);

    /**
     * 根据用户ID查询
     */
    List<FoundItem> selectByUserId(String userId);

    /**
     * 根据关键词搜索
     */
    List<FoundItem> searchByKeyword(@Param("keyword") String keyword,
                                    @Param("category") String category,
                                    @Param("location") String location);

    /**
     * 更新状态
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新记录
     */
    int update(FoundItem foundItem);
}
