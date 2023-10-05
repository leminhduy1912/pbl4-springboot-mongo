package com.pbl4.garbageclassification.services;

import com.pbl4.garbageclassification.collections.KindOfGarbage;

import java.util.List;

public interface IKindOfGarbageService {
    KindOfGarbage save(KindOfGarbage garbage);
    void delete(String id);
    List<KindOfGarbage> findAll();

}
