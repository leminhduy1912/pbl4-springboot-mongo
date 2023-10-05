package com.pbl4.garbageclassification.controllers;

import com.pbl4.garbageclassification.collections.KindOfGarbage;
import com.pbl4.garbageclassification.services.IKindOfGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KindOfGarbageController {
    @Autowired
    private IKindOfGarbageService kindOfGarbageService;
    @GetMapping("/api/kindOfGarbage")
    public List<KindOfGarbage> getAllKindOfGarbage(){
        return kindOfGarbageService.findAll();
    }
    @PostMapping("/api/kindOfGarbage")
    public KindOfGarbage addKindOfGarbage(@RequestBody KindOfGarbage kindOfGarbage){
    return kindOfGarbageService.save(kindOfGarbage);
}
    @PutMapping("/api/kindOfGarbage")
    public KindOfGarbage updateKindOfGarbage(@RequestBody KindOfGarbage kindOfGarbage){
      return kindOfGarbageService.save(kindOfGarbage);
}
    @DeleteMapping("/api/kindOfGarbage")
    public void deleteKindOfGarbage(@RequestParam String id){
       kindOfGarbageService.delete(id);

    }
}
