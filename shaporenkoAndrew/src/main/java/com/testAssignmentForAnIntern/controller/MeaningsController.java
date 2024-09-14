package com.testAssignmentForAnIntern.controller;

import com.testAssignmentForAnIntern.model.Meaning;
import com.testAssignmentForAnIntern.service.MeaningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//Класс контроллер обрабатывающий HTTP запросы
@Controller
@RequestMapping("/meanings")
public class MeaningsController {

    private final MeaningsService meaningsService;

    @Autowired
    public MeaningsController(MeaningsService meaningsService){
        this.meaningsService = meaningsService;
    }

    // Возвращает страницу с полным списком доступных ключей,
    // в случае отсутсвия ключей в хранилище возвращает страницу отсутсвия данных
    @GetMapping()
    public String getAll(Model model){
        if (meaningsService.getAll() != null){
            model.addAttribute("meaningArray", meaningsService.getAll());
            return "meanings";
        }
        else if (meaningsService.getAll() == null){
            return "dataAbsence";
        }
        return "error";
    }

    /*
        Возвращает страницу с данными значение (ключ, значение, ttl (оставщееся на данный момент))
        по переданному в качестве параметра HTTP запроса ключу,
        в случае отсутсвия ключа возвращает страницу отсутсвия данных
    */
    @GetMapping("/get")
    public String getMeaning(@RequestParam("key") String key, Model model){
        if(meaningsService.get(key) != null) {
            model.addAttribute("meaning", meaningsService.get(key));
            return "meaning";
        }
        else if (meaningsService.get(key) == null){
            return "dataAbsence";
        }
        return "error";
    }

    /*
        Возвращает страницу с созданным и добавленным ключ-значением,
        принемает в качестве параметров HTTP запроса ключ, значение и ttl (может не указывается),
        и перенаправляет данные на POST запрос
    */
    @GetMapping("/new")
    public String create(@RequestParam("key") String key,
                       @RequestParam("content") String content,
                       @RequestParam(value = "ttl", required = false) Integer ttl, Model model){
        return this.createMeaning(key, content, ttl, model);
    }

    /*
        Принимает данные от GET запроса, создаёт объект типа Folder и добавляет его в сервис,
        возвращает страницу с успешно добавленными данными
     */
    @PostMapping()
    public String createMeaning(@RequestParam("key") String key,
                              @RequestParam("content") String content,
                              @RequestParam(value = "ttl", required = false) Integer ttl, Model model){
        Meaning meaning = new Meaning(key, content, ttl);
        if (meaningsService.set(key, meaning)) {
            model.addAttribute("meaning", meaning);
            return "successPage";
        }
        else {
            return "error";
        }
    }

    /*
        Принимает в качестве параметра HTTP запроса ключ
        и перенаправляет его в DELETE запрос.
        Возвращает страницу с удалёнными данным.
     */
    @GetMapping("/delete")
    public String delete(@RequestParam("key") String key, Model model){
        return this.deleteMeaning(key, model);
    }

    /*
        Удаляет данные из сервича по переданному ключу и возвращает страницу с удалёнными данными
     */
    @DeleteMapping()
    public String deleteMeaning(String key, Model model){
        if (meaningsService.get(key) != null){
            model.addAttribute("meaning", meaningsService.delete(key));
            return "meaning";
        }
        else if(meaningsService.get(key) == null){
            return "dataAbsence";
        }
        return "error";
    }

    /*
        Создаёт файл с названием переданным в качестве параметра HTTP запроса
        и сохраняет в нём все ключ-значения из сервиса
     */
    @GetMapping("/dump")
    public String dump(@RequestParam("fileName") String fileName){
        if(meaningsService.dump(fileName)){
            meaningsService.dump(fileName);
            return "complite";
        }
        return "error";
    }

    /*
        Загружает файл с переданным названием в качестве параметра HTTP запроса
        и сохраняет все ключ-значения в сервиса
     */
    @GetMapping("/load")
    public String load(@RequestParam("fileName") String fileName){
        if(meaningsService.load(fileName)){
            meaningsService.load(fileName);
            return "redirect:/meaning";
        }
        return "error";
    }
}
