package io.github.zh.mylangchain4jtools.service.impl;

import io.github.zh.mylangchain4jtools.dto.FoundItemRegisterResult;
import io.github.zh.mylangchain4jtools.dto.LostItemRegisterResult;
import io.github.zh.mylangchain4jtools.entity.FoundItem;
import io.github.zh.mylangchain4jtools.entity.LostItem;
import io.github.zh.mylangchain4jtools.mapper.FoundItemMapper;
import io.github.zh.mylangchain4jtools.mapper.LostItemMapper;
import io.github.zh.mylangchain4jtools.service.LostAndFoundService;
import io.github.zh.mylangchain4jtools.tools.MyTools;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class LostAndFoundServiceImpl implements LostAndFoundService {

    @Resource
    private LostItemMapper lostItemMapper;

    @Resource
    private FoundItemMapper foundItemMapper;

    @Resource
    private MyTools myTools;


    @Override
    public Long saveLostItem(String userId, LostItemRegisterResult result) {
        if (!result.getCanCompleteRegistration()) {
//result.getCanCompleteRegistration()  == false ||
            log.info("保存物品丢失信息：{}", result);
            return null;
        }
        LostItem lostItem = new LostItem();
        lostItem.setUserId(userId);
        lostItem.setItemName(result.getItemName());
        lostItem.setItemDescription(result.getItemDescription());
        lostItem.setCategory(result.getCategory());
        lostItem.setLostLocation(result.getLostLocation());
        lostItem.setLostTime(result.getLostTime());
        lostItem.setContactInfo(result.getContactInfo());
        lostItem.setContactName(result.getContactName());
        lostItem.setRemarks(result.getRemarks());
        lostItem.setStatus(0);

        lostItemMapper.insert(lostItem);
        log.info("丢失物品登记成功，ID：{}", lostItem.getId());
        return lostItem.getId();
    }


    @Override
    public Long saveFoundItem(String userId, FoundItemRegisterResult result) {
        if (!result.getCanCompleteRegistration()) {
            //result.getCanCompleteRegistration() == null ||
            log.info("拾到物品信息不完整，暂不保存到数据库");
            return null;
        }
        FoundItem foundItem = new FoundItem();
        foundItem.setUserId(userId);
        foundItem.setItemName(result.getItemName());
        foundItem.setItemDescription(result.getItemDescription());
        foundItem.setCategory(result.getCategory());
        foundItem.setFoundLocation(result.getFoundLocation());
        foundItem.setContactInfo(result.getContactInfo());
        foundItem.setContactName(result.getContactName());
        foundItem.setRemarks(result.getRemarks());
        foundItem.setStatus(0);

        foundItemMapper.insert(foundItem);
        log.info("拾到物品登记成功，ID：{}", foundItem.getId());
        return foundItem.getId();
    }

    @Override
    public List<LostItem> searchLostItem(String keyword, String category, String location) {
        return lostItemMapper.searchByKeyword(keyword, category, location);
    }

    @Override
    public List<FoundItem> searchFoundItem(String keyword, String category, String location) {
        return foundItemMapper.searchByKeyword(keyword, category, location);
    }

    @Override
    public List<LostItem> getUserLostItem(String userId) {
        return lostItemMapper.selectByUserId(userId);
    }

    @Override
    public List<FoundItem> getUserFoundItem(String userId) {
        return foundItemMapper.selectByUserId(userId);
    }
}
