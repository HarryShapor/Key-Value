package com.testAssignmentForAnIntern.controller;


import com.testAssignmentForAnIntern.model.Meaning;
import com.testAssignmentForAnIntern.service.MeaningsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MeaningsController.class)
public class MeaningsControllerTest {

     @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeaningsService meaningsService;

    @Test
    void getMeaning() throws Exception {
        Meaning meaning = new Meaning("Ключ_1", "Значение_1", 3000);
        Mockito.when(meaningsService.get("Ключ_1")).thenReturn(meaning);
        this.mockMvc.perform(get("/meanings/get?key=Ключ_1")).
                andExpect(view().name("meaning")).
                andExpect(model().attribute("meaning", meaning));
    }

    @Test
    void getAllValue() throws Exception {
        Meaning meaningOne = new Meaning("Ключ_1", "Значение_1", 256);
        Meaning meaningTwo = new Meaning("Ключ_2", "Значени_2", 512);
        Meaning meaningThree = new Meaning("Ключ_3", "Значение_3");
        List<Meaning> meaningList = List.of(meaningOne, meaningTwo, meaningThree);
        Mockito.when(meaningsService.getAll()).thenReturn(meaningList);

        this.mockMvc.perform(get("/meanings")).andExpect(status().isOk()).
                andExpect(view().name("meanings")).
                andExpect(model().attribute("meaningArray", meaningList));

    }
}
