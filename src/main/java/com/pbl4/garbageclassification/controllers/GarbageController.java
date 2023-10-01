package com.pbl4.garbageclassification.controllers;



import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.services.IGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GarbageController {
    @Autowired
    private IGarbageService garbageService;
    @PostMapping("/api/garbage")
    public Long save(@RequestBody Garbage garbage){
        return garbageService.save(garbage);
    }
}
