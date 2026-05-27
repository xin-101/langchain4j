package io.github.zh.mylangchain4jtools;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyLangchain4jToolsApplication {

    public static void main(String[] args) {
        /*读取env文件*/
        Dotenv dotenv = Dotenv.configure().load();
        /*把加载的env文件设置到环境变量中*/
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        SpringApplication.run(MyLangchain4jToolsApplication.class, args);
    }

}
