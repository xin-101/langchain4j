package io.github.zh.mylangchain4j;

import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyLangchain4jApplicationTests {

	@Test
	void contextLoads() {

		OpenAiChatModel model = OpenAiChatModel.builder()
				.baseUrl("http://langchain4j.dev/demo/openai/v1")
				.apiKey("demo")
				.modelName("gpt-4o-mini")
				.build();

		String answer = model.chat("你是谁？可以为我做什么？");
		System.out.println(answer); // Hello World

	}

	@Resource
	private OpenAiChatModel model;

	@Test
	void langchain4jSpringbootTest(){
		String answer=model.chat("你是基于什么模型");
		System.out.println(answer);
	}

}
