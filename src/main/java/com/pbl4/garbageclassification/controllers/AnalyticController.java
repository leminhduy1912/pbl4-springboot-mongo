package com.pbl4.garbageclassification.controllers;

import com.pbl4.garbageclassification.results.ResponeObject;
import com.pbl4.garbageclassification.services.IGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AnalyticController {
    @Autowired
    private IGarbageService garbageService;

    @GetMapping("/api/v1/analytic")
    public ResponseEntity<?> analytic() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponeObject.builder()
                        .status(200)
                        .message("Query successfully")
                        .data(garbageService.analyticClassOfGarbage())
                        .build()
        );
    }
    @GetMapping("/analytic")
    public String analytic(Model model){
        Map<String,Long> result = garbageService.analyticClassOfGarbage();
        List<Long> analytic = new ArrayList<>();
        analytic.add(result.get("Hazadous"));
        analytic.add(result.get("Recycle"));
        analytic.add(result.get("Other"));
        analytic.add(result.get("Glass"));
        model.addAttribute("num",analytic);
        return "analytic";
    }
}
