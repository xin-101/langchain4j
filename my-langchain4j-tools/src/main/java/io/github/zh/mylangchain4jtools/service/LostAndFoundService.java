package io.github.zh.mylangchain4jtools.service;

import io.github.zh.mylangchain4jtools.dto.FoundItemRegisterResult;
import io.github.zh.mylangchain4jtools.dto.LostItemRegisterResult;
import io.github.zh.mylangchain4jtools.entity.FoundItem;
import io.github.zh.mylangchain4jtools.entity.LostItem;

import java.util.List;

public interface LostAndFoundService {

    /**
     * 保存丢失物品信息
     */
    Long saveLostItem(String userId, LostItemRegisterResult result);
    /**
     * 保存拾到物品信息
     */
    Long saveFoundItem(String userId, FoundItemRegisterResult result);
    /**
     * 根据关键词搜索丢失物品
     */
    List<LostItem> searchLostItem(String keyword, String category, String location);
    /**
     * 根据关键词搜索拾到物品
     */
    List<FoundItem> searchFoundItem(String keyword, String category, String location);
    /**
     * 获取用户的丢失物品记录
     */
    List<LostItem> getUserLostItem(String userId);
    /**
     * 获取用户的拾到物品记录
     */
    List<FoundItem> getUserFoundItem(String userId);


}
