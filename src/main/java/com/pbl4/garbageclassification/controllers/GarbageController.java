package com.pbl4.garbageclassification.controllers;
import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.results.ResponeObject;
import com.pbl4.garbageclassification.services.IGarbageService;
import com.pbl4.garbageclassification.services.IImageService;
import com.pbl4.garbageclassification.services.IQueueProcess;
import com.pbl4.garbageclassification.services.impl.GetResultAIServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@Controller
public class GarbageController {
    @Autowired
    private IImageService imageService;
    @Autowired
    private IQueueProcess queueProcess;
    @Autowired
    private GetResultAIServer getResultAIServer;
    @Autowired
    private IGarbageService garbageService;
    @GetMapping("/result")
    public String index(Model model) {

        return "classify-garbage";
    }

    @GetMapping("/listGarbage")
    public String listGarbage(Model model) {
        List<Garbage> garbageList = garbageService.findAll();
//        System.out.println("img receive = " + fileName);
        model.addAttribute("garbages", garbageList);
        return "list-garbage";
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }








}



