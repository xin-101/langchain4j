package io.github.zh.mylangchain4jtools.entity;

public class LostItem {
    private Long id;
    private String userId;
    private String itemName;
    private String itemDescription;
    private String category;
    private String lostLocation;
    private String lostTime;
    private String contactInfo;
    private String contactName;
    private String remarks;
    private Integer status;
    private String createTime;
    private String updateTime;


    public LostItem() {
    }

    public LostItem(Long id, String userId, String itemName, String itemDescription, String category, String lostLocation, String lostTime, String contactInfo, String contactName, String remarks, Integer status, String createTime, String updateTime) {
        this.id = id;
        this.userId = userId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.category = category;
        this.lostLocation = lostLocation;
        this.lostTime = lostTime;
        this.contactInfo = contactInfo;
        this.contactName = contactName;
        this.remarks = remarks;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLostLocation() {
        return lostLocation;
    }

    public void setLostLocation(String lostLocation) {
        this.lostLocation = lostLocation;
    }

    public String getLostTime() {
        return lostTime;
    }

    public void setLostTime(String lostTime) {
        this.lostTime = lostTime;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {

        return createTime;
    }

    public void setCreateTime(String createTime) {

        this.createTime = createTime;
    }


}
