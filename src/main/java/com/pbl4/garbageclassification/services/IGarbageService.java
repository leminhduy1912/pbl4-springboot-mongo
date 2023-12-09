package com.pbl4.garbageclassification.services;


import com.pbl4.garbageclassification.collections.Garbage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IGarbageService {
    List<Garbage> findAll();
    String save(String kindOfGarbage, String fileName,String classOfGarbage);
//    String update(String garbageId,String kindOfGarbage,String numOfBin, MultipartFile[] files);
    String update(Garbage garbage,MultipartFile file);
    void delete(String id);
    Garbage findById(String id);
    boolean isExist(String id);
    Long count();
    Long  countByKindOfGarbage(String kindOfGarbage);
    Long  countByClassOfGarbage(String classOfGarbage);
    Map<String,Long> analyticClassOfGarbage();
}
