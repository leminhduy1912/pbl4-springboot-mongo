package com.pbl4.garbageclassification.services;


import com.pbl4.garbageclassification.collections.Garbage;

import java.util.List;

public interface IGarbageService {
    List<Garbage> findAll();
    String save(Garbage garbage);
    void delete(String id);
}
