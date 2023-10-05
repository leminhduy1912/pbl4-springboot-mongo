package com.pbl4.garbageclassification.controllers;



import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.services.IGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class GarbageController {
    @Autowired
    private IGarbageService garbageService;
    @GetMapping("/api/garbarge")
    public List<Garbage> getAll(){
        return garbageService.findAll();
    }
    @PostMapping("/api/garbage")
    public String save(@RequestBody Garbage garbage){
        return garbageService.save(garbage);
    }
    @DeleteMapping("/api/garbage")
    public void delete(@RequestParam String id){
        garbageService.delete(id);
    }
}
