package com.pbl4.garbageclassification.services.impl;


import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.repositories.GarbageRepository;
import com.pbl4.garbageclassification.services.IGarbageService;
import com.pbl4.garbageclassification.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
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
    public String save(String kindOfGarbage,String numOfBin, MultipartFile[] files) {
        Garbage garbage =  new Garbage();
        Set<String> imageFilesName = new HashSet<>();
        for (MultipartFile multipartFile : files){
            imageFilesName.add(iImageService.storeFile(multipartFile));
        }
        garbage.setKindOfGarbage(kindOfGarbage);
        garbage.setNumOfBin(numOfBin);
        garbage.setImages(imageFilesName);
        return garbageRepository.save(garbage).getGabargeId();
    }

    @Override
    public String update(String garbageId, String kindOfGarbage, String numOfBin, MultipartFile[] files) {
        boolean isExist = isExist(garbageId);

        if (isExist){
            Garbage garbage = garbageRepository.findById(garbageId).get();
            Set<String> fileNamesExist = garbage.getImages();
            iImageService.delete(fileNamesExist);

            Set<String> images = new HashSet<>();
            for (int i=0 ;i< files.length;i++){
                images.add(iImageService.storeFile(files[i]));
            }

            garbage.setKindOfGarbage(kindOfGarbage);
            garbage.setImages(images);
            return garbageRepository.save(garbage).getGabargeId();
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
}
