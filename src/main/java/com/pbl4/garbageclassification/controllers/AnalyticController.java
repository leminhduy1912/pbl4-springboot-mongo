package com.pbl4.garbageclassification.controllers;

import com.pbl4.garbageclassification.services.IGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class AnalyticController {
    @Autowired
    private IGarbageService garbageService;
    @GetMapping("garbage/analytic")
    public Map<String, Long> analytic(){
      return garbageService.analyticKindOfGarbage();

    }
}
