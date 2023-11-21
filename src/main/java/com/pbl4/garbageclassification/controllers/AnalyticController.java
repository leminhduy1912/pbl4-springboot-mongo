package com.pbl4.garbageclassification.controllers;

import com.pbl4.garbageclassification.results.ResponeObject;
import com.pbl4.garbageclassification.services.IGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticController {
    @Autowired
    private IGarbageService garbageService;
    @MessageMapping("/analytic")
    @SendTo("/topic/analytic")
    public ResponseEntity<?> analytic() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponeObject.builder()
                        .status(200)
                        .message("Query successfully")
                        .data(garbageService.analyticKindOfGarbage())
                        .build()
        );
    }
}
