package com.pbl4.garbageclassification.services.impl;


import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.repositories.GarbageRepository;
import com.pbl4.garbageclassification.services.IGarbageService;
import com.pbl4.garbageclassification.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GarbageService implements IGarbageService {
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private IImageService iImageService;

    @Override
    public List<Garbage> findAll() {
        return garbageRepository.findAll();
    }

    @Override
    public String save(String kindOfGarbage, String fileName,String classOfGarbage) {
        Garbage garbage =  new Garbage();
        garbage.setClassOfGarbage(classOfGarbage);
        garbage.setKindOfGarbage(kindOfGarbage);
        garbage.setImage(fileName);
        garbage.setTimeCreate(LocalDateTime.now());
        return garbageRepository.save(garbage).getGabargeId();
    }

    @Override
    public String update(Garbage garbage,MultipartFile file) {
        boolean isExist = isExist(garbage.getGabargeId());

        if (isExist){
            Garbage garbageOld = garbageRepository.findById(garbage.getGabargeId()).get();
            if(file != null){
                String fileNamesExist = garbageOld.getImage();
                //iImageService.delete(fileNamesExist);

                String image  = iImageService.storeFile(file);

                //garbageOld.setImages(images);
            }
            garbageOld.setKindOfGarbage(garbage.getKindOfGarbage());
            garbageOld.setClassOfGarbage(garbage.getClassOfGarbage());
            return garbageRepository.save(garbageOld).getGabargeId();
        } else {
            return null;
        }
    }


    @Override
    public void delete(String id) {
        boolean isExist = isExist(id);
        if (isExist){
            garbageRepository.deleteById(id);
        }

    }

    @Override
    public Garbage findById(String id) {
        boolean isExist = isExist(id);
        if (isExist){
            return garbageRepository.findById(id).get();
        }
        else {return null;}
    }

    @Override
    public boolean isExist(String id) {
        return garbageRepository.existsById(id);

    }

    @Override
    public Long count() {
        return garbageRepository.count();
    }

    @Override
    public Long countByKindOfGarbage(String kindOfGarbage) {
        return garbageRepository.countByKindOfGarbage(kindOfGarbage);
    }

    @Override
    public Long countByClassOfGarbage(String classOfGarbage) {
        return garbageRepository.countByClassOfGarbage(classOfGarbage);
    }

    @Override
    public Map<String,Long> analyticClassOfGarbage() {
        Map<String,Long> result = new HashMap<>();
        result.put("Glass",countByClassOfGarbage("glass"));
        result.put("Hazadous",countByClassOfGarbage("hazadous"));
        result.put("Recycle",countByClassOfGarbage("recycle"));
        result.put("Other",countByClassOfGarbage("other"));
        return result;
    }
}
