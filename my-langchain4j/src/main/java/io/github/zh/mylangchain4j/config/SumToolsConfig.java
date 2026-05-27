package io.github.zh.mylangchain4j.config;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class SumToolsConfig {

    //两数相加
    @Tool(name = "add",value = "我会给你两个数字，你将计算两数相加")
    public int add(int a, int b) {
        System.out.println("add："+a+"+"+b);
        return a + b;
    }
}
