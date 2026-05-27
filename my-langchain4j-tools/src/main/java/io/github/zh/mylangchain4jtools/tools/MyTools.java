package io.github.zh.mylangchain4jtools.tools;

import cn.hutool.core.date.LocalDateTimeUtil;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyTools {

    @Tool("获取当前用户的年纪")
    public String getAge(String age) {
        return "18";
    }

    @Tool("获取当前时间 输出为yyyy-MM-dd")
    public String getTime() {

        // 获取当前时间
        LocalDateTime now = LocalDateTimeUtil.now();

        return LocalDateTimeUtil.format(now, "yyyy-MM-dd");
    }
}
