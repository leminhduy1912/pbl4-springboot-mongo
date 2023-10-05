package com.pbl4.garbageclassification.services.impl;

import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.collections.KindOfGarbage;
import com.pbl4.garbageclassification.repositories.KindOfGarbageRepository;
import com.pbl4.garbageclassification.services.IGarbageService;
import com.pbl4.garbageclassification.services.IKindOfGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KindOfGarbageService implements IKindOfGarbageService {
   @Autowired
   private KindOfGarbageRepository kindOfGarbageRepository;

    @Override
    public KindOfGarbage save(KindOfGarbage garbage) {
        if(garbage.getKindOfGarbage_id() != null){
                     Optional<KindOfGarbage> kindOfGarbage = kindOfGarbageRepository.findById(garbage.getKindOfGarbage_id());
                     if (kindOfGarbage.isPresent()){
                         KindOfGarbage newKind = new KindOfGarbage();
                         newKind.setBin_num(kindOfGarbage.get().getBin_num());
                         newKind.setKindOfGabage_name(kindOfGarbage.get().getKindOfGabage_name());
                         newKind.setKindOfGarbage_id(garbage.getKindOfGarbage_id());
                         return kindOfGarbageRepository.save(newKind);
                     }

        }
        return kindOfGarbageRepository.save(garbage);
    }

    @Override
    public void delete(String id) {
        kindOfGarbageRepository.deleteById(id);
    }

    @Override
    public List<KindOfGarbage> findAll() {
        return kindOfGarbageRepository.findAll();
    }
}
