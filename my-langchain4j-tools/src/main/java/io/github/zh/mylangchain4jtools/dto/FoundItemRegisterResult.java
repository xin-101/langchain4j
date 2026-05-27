package io.github.zh.mylangchain4jtools.dto;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
@Description("捡到物品登记")
public class FoundItemRegisterResult {

    @Description("物品名称")
    private String itemName;

    @Description("物品描述")
    private String itemDescription;

    @Description("物品类别")
    private String category;

    @Description("捡到地点")
    private String foundLocation;

    @Description("联系方式")
    private String contactInfo;

    @Description("联系人姓名")
    private String contactName;

    @Description("备注信息")
    private String remarks;

    @Description("是否可以完成登记,经过用户同意之后方可登记")
    private boolean canCompleteRegistration;

    @Description("""
            输出内容，告知当前已登记的用户信息，及缺少的信息，以及是否可以登记成功到失物系统，
            如果缺少信息需要引导用户补充信息
            如果信息以及完善，需要输出信息告知到用户，询问用户是否有修改的内容
            当用户确认后，再执行其他操作""")
    private String aiFinalOutputSummary;


    public boolean getCanCompleteRegistration() {
        return canCompleteRegistration;
    }
}
