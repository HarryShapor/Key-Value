package com.testAssignmentForAnIntern.repository;

import com.testAssignmentForAnIntern.model.Meaning;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*Класс для хранения объектов класса Meaning, в качестве хранилища используется HashMap,
    где ключом служить значение типа String, которое является ключом самого объекта Meaning.*/
@Component
public class MeaningsRepository {

    private Map<String, Meaning> meaningMap = new HashMap<>();

    //Возвращает коллекцию Map
    public Map<String, Meaning> getAll(){
        return meaningMap;
    }

    //Возвращает коллекцию ArrayList, которая содержит объекты типа Folder
    public ArrayList<Meaning> getAllMeaning(){
        return new ArrayList<Meaning>(meaningMap.values());
    }

    //Возвращает объект типа Folder по переданному ключу
    public Meaning getMeaning(String key){
        return meaningMap.get(key);
    }

    //Добавляет новое значение в HashMap с переданными значением ключа и объектом Folder
    public void createMeaning(String key, Meaning meaning){
        meaningMap.put(key, meaning);
    }

    //Возвращает объект Folder после удаления из HashMap
    public Meaning deleteMeaning(String key){
        Meaning meaning = meaningMap.get(key);
        meaningMap.remove(key);
        return meaning;
    }

    //Обновляет существующее в HashMap значение типа Folder
    public void updateMeaning(String key, String value, int ttl){
        meaningMap.replace(key, new Meaning(key, value, ttl));
    }

    //Очищает коллекцию HashMap
    public void clear(){
        meaningMap.clear();
    }
}
