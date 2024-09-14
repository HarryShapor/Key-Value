package com.testAssignmentForAnIntern.model;

/*Класс для создания объектов "Ключ-значение"*/
public class Meaning {

    private String key;     //Поле для ключа
    private String content;   //Поле для значения
    private Integer ttl;    //Поле для значение ttl (в секундах)

    public Meaning(){}

    public Meaning(String key, String content){
        this.key = key;
        this.content = content;
        this.ttl = 60;
    }

    public Meaning(String key, String content, Integer ttl){
        this(key, content);
        if(ttl != null){
            this.ttl = ttl;
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }
}
