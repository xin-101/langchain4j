package io.github.zh.mylangchain4j.prompt;

import dev.langchain4j.model.input.structured.StructuredPrompt;

@StructuredPrompt("根据{{book}}医书，解答下方的问题{{question}}")
public class DoctorPrompt {

    private String book;
    private String question;

    public DoctorPrompt(String book, String question) {
        this.book = book;
        this.question = question;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
