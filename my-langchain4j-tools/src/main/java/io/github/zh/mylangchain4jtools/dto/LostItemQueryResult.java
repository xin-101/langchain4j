package io.github.zh.mylangchain4jtools.dto;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
@Description("失物信息查询")
public class LostItemQueryResult {

    @Description("查询关键词，如物品名称、类别等")
    private String queryKeyword;

    @Description("查询的时间范围")
    private String timeRange;

    @Description("查询的地点范围")
    private String locationRange;

    @Description("是否可以执行查询")
    private boolean canExecuteQuery;

    @Description("""
            输出内容，根据用户的查询需求，告知查询结果或引导用户提供更多查询条件
            如果查询条件不足，需要引导用户补充信息
            如果查询到相关信息，需要清晰的展示查询结果
            如果没有查询到相关信息，需要给出友好的提示
            """)
    private String aiFinalOutputSummary;

}
