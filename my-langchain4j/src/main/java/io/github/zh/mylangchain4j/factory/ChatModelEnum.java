package io.github.zh.mylangchain4j.factory;

public enum ChatModelEnum {

    //OPENAI模型
    OPEN_AI(1,"OPEN_AI"),
    //百炼模型
    QWEN_CHAT(2,"QWEN_CHAT"),
        ;

    private Integer code;
    private String name;

    ChatModelEnum(Integer code,String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public static ChatModelEnum getByCode(Integer modelCode) {
        return ChatModelEnum.values()[modelCode-1];
    }

}
