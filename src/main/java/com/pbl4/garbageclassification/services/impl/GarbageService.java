package com.pbl4.garbageclassification.services.impl;


import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.repositories.GarbageRepository;
import com.pbl4.garbageclassification.services.IGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarbageService implements IGarbageService {
    @Autowired
    private GarbageRepository garbageRepository;

    @Override
    public List<Garbage> findAll() {
        return garbageRepository.findAll();
    }

    @Override
    public String save(Garbage garbage) {

        return garbageRepository.save(garbage).getGabargeId();
    }

    @Override
    public String update(Garbage garbage) {
        return garbageRepository.save(garbage).getGabargeId();
    }

    @Override
    public void delete(String id) {
         garbageRepository.deleteById(id);
    }

    @Override
    public Optional<Garbage> finbById(String id) {
        Optional<Garbage> garbage = garbageRepository.findById(id);
       if(garbage.isPresent()){
           return garbage;
       }
        return garbage;
    }

    @Override
    public boolean isExist(String id) {
        return garbageRepository.existsById(id);

    }
}
