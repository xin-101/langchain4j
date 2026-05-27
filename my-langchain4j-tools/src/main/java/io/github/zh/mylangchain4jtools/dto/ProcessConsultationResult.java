package io.github.zh.mylangchain4jtools.dto;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
@Description("流程咨询")
public class ProcessConsultationResult {

    @Description("咨询的问题类型，如：登记流程、查询方法、联系方式等")
    private String consultationType;

    @Description("咨询的详细内容")
    private String consultationQuestion;

    @Description("是否可以提供咨询答案")
    private boolean canProvideAnswer;

    @Description("""
            输出内容，根据用户的咨询问题，提供详细的流程说明或操作指导，
            需要清晰的解答用户的疑问，提供具体的操作步骤
            如果问题不在服务范围内，需要用户友好的引导到正确的咨询渠道
            """)
    private String aiFinalOutputSummary;
}
