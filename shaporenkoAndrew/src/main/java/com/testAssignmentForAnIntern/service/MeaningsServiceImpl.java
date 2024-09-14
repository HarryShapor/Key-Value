package com.testAssignmentForAnIntern.service;

import com.testAssignmentForAnIntern.model.Meaning;
import com.testAssignmentForAnIntern.repository.MeaningsRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* Класс реализующий интерфейс MeaningsService */
@Service
public class MeaningsServiceImpl implements MeaningsService{

    private final MeaningsRepository meaningsRepository = new MeaningsRepository();

    @Override
    public List<Meaning> getAll() {
        if (!meaningsRepository.getAll().isEmpty()) {
            return meaningsRepository.getAllMeaning();
        }
        return null;
    }

    @Override
    public boolean set(String key, Meaning meaning) {
        try{
            if (meaningsRepository.getMeaning(key) == null) {
                meaningsRepository.createMeaning(key, meaning);
            } else {
                meaningsRepository.updateMeaning(key, meaning.getContent(), meaning.getTtl());
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Meaning get(String key) {
        if (meaningsRepository.getAll().containsKey(key)){
            return meaningsRepository.getMeaning(key);
        }
        return null;
    }

    @Override
    public Meaning delete(String key) {
        if(meaningsRepository.getAll().containsKey(key)){
            return meaningsRepository.deleteMeaning(key);
        }
        return null;
    }

    @Override
    public boolean dump(String fileName) {
        ArrayList<Meaning> meaningArrayList = (ArrayList<Meaning>) this.getAll();
        if (Files.notExists(Path.of("src\\main\\resources\\storageFile\\" +  fileName + ".txt"))) {
            Path path = Paths.get("src\\main\\resources\\storageFile\\" +  fileName + ".txt");
            try {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try (FileWriter fileWriter = new FileWriter("src\\main\\resources\\storageFile\\"
                    +  fileName + ".txt")) {
                for (int i = 0; i < meaningArrayList.size(); i++) {
                    String keyString = meaningArrayList.get(i).getKey();
                    String contentString = meaningArrayList.get(i).getContent();
                    String ttlString = Integer.toString(meaningArrayList.get(i).getTtl());
                    String meaningString = new String(keyString + ":" + contentString
                            + ":" + ttlString + "\n\n");
                    fileWriter.write(meaningString);
                }
                fileWriter.close();
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public boolean load(String fileName) {
        try {
            if (!Files.notExists(Path.of("src\\main\\resources\\storageFile\\"
                    + fileName + ".txt"))) {
                FileReader fileReader = null;
                fileReader = new FileReader("src\\main\\resources\\storageFile\\"
                        + fileName + ".txt");
                Scanner scanner = new Scanner(fileReader);
                this.clear();
                while (scanner.hasNextLine()) {
                    String[] meaningString = scanner.nextLine().split(":");
                    Meaning meaning = new Meaning(meaningString[0], meaningString[1], Integer.parseInt(meaningString[2]));
                    this.set(meaning.getKey(), meaning);
                    scanner.nextLine();
                }
                fileReader.close();
                return true;
            }
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void ttlDelete() {
        for(Meaning meaning : meaningsRepository.getAllMeaning()){
            if (meaning.getTtl() == 1)
                meaningsRepository.deleteMeaning(meaning.getKey());
            else
                meaning.setTtl(meaning.getTtl()-1);
        }
    }

    @Override
    public void clear() {
        meaningsRepository.clear();
    }
}
