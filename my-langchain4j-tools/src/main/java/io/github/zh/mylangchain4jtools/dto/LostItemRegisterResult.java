package io.github.zh.mylangchain4jtools.dto;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
@Description("物品丢失登记")
public class LostItemRegisterResult {

    @Description("物品名称")
    private String itemName;

    @Description("物品详细描述，包括颜色、大小、特征等")
    private String itemDescription;

    @Description("物品类别，如：电子产品、证件、钱包、钥匙、衣服、书籍等")
    private String category;

    @Description("物品丢失地点")
    private String lostLocation;

    @Description("物品丢失时间")
    private String lostTime;

    @Description("联系方式，如手机号或邮箱")
    private String contactInfo;

    @Description("联系人姓名")
    private String contactName;

    @Description("备注信息")
    private String remarks;

    @Description("是否可以完成登记")
    private boolean canCompleteRegistration;

    @Description("""
            输出内容，告知当前已登记的用户信息，及缺少的信息，以及是否可以登记成功到失物系统，
            如果缺少信息需要引导用户补充信息
            如果信息以及完善，需要输出信息告知到用户，询问用户是否有修改的内容
            当用户确认后，再执行其他操作
            """)
    private String aiFinalOutputSummary;

    public boolean getCanCompleteRegistration() {
        canCompleteRegistration = itemName != null && itemDescription != null && category != null && lostLocation != null && lostTime != null && contactInfo != null && contactName != null;
        return canCompleteRegistration;
    }
}
