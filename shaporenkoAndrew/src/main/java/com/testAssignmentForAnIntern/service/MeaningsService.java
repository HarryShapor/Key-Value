package com.testAssignmentForAnIntern.service;

import com.testAssignmentForAnIntern.model.Meaning;
import java.util.List;

public interface MeaningsService {

    List<Meaning> getAll();

    boolean set(String key, Meaning folder);

    Meaning get(String key);

    Meaning delete(String key);

    boolean dump(String fileName);

    boolean load(String fileName);

    void ttlDelete();

    void clear();

}
