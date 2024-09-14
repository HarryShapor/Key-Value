package com.testAssignmentForAnIntern.service;

import com.testAssignmentForAnIntern.model.Meaning;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MeaningsServiceImplTest {

    private MeaningsService meaningsService = new MeaningsServiceImpl();

    @Test
    void get(){
        Meaning meaning = new Meaning("Ключ_1", "Значение_1", 300);
        meaningsService.set(meaning.getKey(), meaning);
        Meaning testFolder = meaningsService.get("Ключ_1");
        assertEquals(meaning, testFolder);
        meaningsService.delete("Ключ_1");
    }

    @Test
    void set(){
        Meaning meaning = new Meaning("Ключ_1", "Значение_1", 120);
        meaningsService.set(meaning.getKey(), meaning);
        Meaning testMeaning = meaningsService.get("Ключ_1");
        assertEquals(meaning.getKey(), testMeaning.getKey());
        assertEquals(meaning.getContent(), testMeaning.getContent());
        assertEquals(meaning.getTtl(), testMeaning.getTtl());
        meaningsService.delete("Ключ_1");
    }

    @Test
    void setDefaulTtl(){
        Meaning meaning = new Meaning("Ключ_1", "Значение_1");
        meaningsService.set(meaning.getKey(), meaning);
        Meaning testFolder = meaningsService.get("Ключ_1");
        assertEquals(testFolder.getTtl(), 60);
        meaningsService.delete("Ключ_1");
    }

    @Test
    void setUpdate(){
        Meaning meaning = new Meaning("Ключ_1", "Значение_1", 300);
        Meaning meaningSecond = new Meaning("Ключ_1", "Значение_2", 150);
        meaningsService.set(meaning.getKey(), meaning);
        Meaning testMeaning = meaningsService.get("Ключ_1");
        meaningsService.set(meaningSecond.getKey(), meaningSecond);
        Meaning testMeaningSecond = meaningsService.get("Ключ_1");

        assertEquals(testMeaning.getKey(), testMeaningSecond.getKey());
        assertNotEquals(testMeaning.getContent(), testMeaningSecond.getContent());
        assertNotEquals(testMeaning.getTtl(), testMeaningSecond.getTtl());
        meaningsService.delete("Ключ_1");
    }

    @Test
    void delete(){
        Meaning meaning = new Meaning("Ключ_1", "Значение_1", 300);
        meaningsService.set(meaning.getKey(), meaning);
        meaningsService.get("Ключ_1");
        meaningsService.delete("Ключ_1");
        assertNull(meaningsService.get("Ключ_1"));
    }

    @Test
    void dumpAndLoad(){
        Meaning meaning = new Meaning("Ключ_1", "Значение_1", 300);
        Meaning meaningSecond = new Meaning("Ключ_2", "Значение_2", 123);
        Meaning meaningThird = new Meaning("Ключ_3", "Значение_3");

        meaningsService.set(meaning.getKey(), meaning);
        meaningsService.set(meaningSecond.getKey(), meaningSecond);

        meaningsService.dump("testFile");
        meaningsService.set(meaningThird.getKey(), meaningThird);
        meaningsService.delete("Ключ_1");
        meaningsService.delete("Ключ_2");

        meaningsService.load("testFile");
        assertNotNull(meaningsService.get("Ключ_1"));
        assertNotNull(meaningsService.get("Ключ_2"));
        assertNull(meaningsService.get("Ключ_3"));
        Meaning testMeaning = meaningsService.get("Ключ_2");
        assertEquals(meaningSecond.getKey(), testMeaning.getKey());
        assertEquals(meaningSecond.getContent(), testMeaning.getContent());
        assertEquals(meaningSecond.getTtl(), testMeaning.getTtl());
        meaningsService.delete("Ключ_1");
        meaningsService.delete("Ключ_2");
    }
}
