package com.pbl4.garbageclassification.services.impl;


import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.repositories.GarbageRepository;
import com.pbl4.garbageclassification.services.IGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GarbageService implements IGarbageService {
    @Autowired
    private GarbageRepository garbageRepository;
    @Override
    public Long save(Garbage garbage) {
        return garbageRepository.save(garbage).getGabargeId();
    }
}
