package com.pbl4.garbageclassification.services;


import com.pbl4.garbageclassification.collections.Garbage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IGarbageService {
    List<Garbage> findAll();
    String save(String kindOfGarbage,String numOfBin, MultipartFile[] files);
    String update(String garbageId,String kindOfGarbage,String numOfBin, MultipartFile[] files);
    void delete(String id);
    Garbage findById(String id);
    boolean isExist(String id);
}
