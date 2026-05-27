package io.github.zh.mylangchain4j;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyStore;

@SpringBootApplication
public class MyLangchain4jApplication {

	public static void main(String[] args) {
		/*读取env文件*/
		Dotenv dotenv = Dotenv.configure().load();
		/*把加载的env文件设置到环境变量中*/
		dotenv.entries().forEach(entry->System.setProperty(entry.getKey(), entry.getValue()));


		SpringApplication.run(MyLangchain4jApplication.class, args);
	}

}
