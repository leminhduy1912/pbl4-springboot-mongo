package com.pbl4.garbageclassification.services.impl;


import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.repositories.GarbageRepository;
import com.pbl4.garbageclassification.services.IGarbageService;
import com.pbl4.garbageclassification.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public String save(String kindOfGarbage, MultipartFile[] files) {
        Garbage garbage =  new Garbage();
        Set<String> imageFilesName = new HashSet<>();
        for (MultipartFile multipartFile : files){
            imageFilesName.add(iImageService.storeFile(multipartFile));
        }
        garbage.setKindOfGarbage(kindOfGarbage);

        garbage.setImages(imageFilesName);
        return garbageRepository.save(garbage).getGabargeId();
    }

    @Override
    public String update(Garbage garbage,MultipartFile[] files) {
        boolean isExist = isExist(garbage.getGabargeId());

        if (isExist){
            Garbage garbageOld = garbageRepository.findById(garbage.getGabargeId()).get();
            if(files != null){
                Set<String> fileNamesExist = garbageOld.getImages();
                iImageService.delete(fileNamesExist);

                Set<String> images = new HashSet<>();
                for (int i=0 ;i< files.length;i++){
                    images.add(iImageService.storeFile(files[i]));
                }
                garbageOld.setImages(images);
            }
            garbageOld.setKindOfGarbage(garbage.getKindOfGarbage());
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
    public Map<String,Long> analyticKindOfGarbage() {
        Map<String,Long> result = new HashMap<>();
        result.put("Glass",countByKindOfGarbage("Glass"));
        System.out.println("Glass" + countByKindOfGarbage("Glass"));
        result.put("Metal",countByKindOfGarbage("Metal"));
        result.put("Recycle",countByKindOfGarbage("Recycle"));
        result.put("Other",countByKindOfGarbage("Other"));
        return result;
    }
}
