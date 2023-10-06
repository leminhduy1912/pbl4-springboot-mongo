package com.pbl4.garbageclassification.services;


import com.pbl4.garbageclassification.collections.Garbage;

import java.util.List;
import java.util.Optional;

public interface IGarbageService {
    List<Garbage> findAll();
    String save(Garbage garbage);
    String update(Garbage garbage);
    void delete(String id);
    Optional<Garbage> finbById(String id);
    boolean isExist(String id);
}
