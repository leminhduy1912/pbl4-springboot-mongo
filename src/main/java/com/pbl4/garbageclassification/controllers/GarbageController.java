package com.pbl4.garbageclassification.controllers;



import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.results.ResponeObject;
import com.pbl4.garbageclassification.services.IGarbageService;
import com.pbl4.garbageclassification.services.impl.ImageStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@Controller
public class GarbageController {
    @Autowired
    private IGarbageService garbageService;




//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
    @GetMapping("/api/garbage")
    public ResponseEntity<ResponeObject> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponeObject(200,"Querry successfully",garbageService.findAll())
        );
    }
    @GetMapping("/api/garbage/{id}")
    public ResponseEntity<ResponeObject> getAll(@PathVariable String id){
        boolean exist = garbageService.isExist(id);
        if(exist){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject(200,"Querry by id successfully",garbageService.finbById(id))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponeObject(200,"Id does not exist","")
        );

    }
    @PostMapping("/api/garbage")
    public ResponseEntity<ResponeObject> save(@RequestBody Garbage garbage){

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponeObject(200,"Created successfully",garbageService.save(garbage))
        );
    }
    @PutMapping("/api/garbage/{id}")
    public ResponseEntity<ResponeObject> update(@RequestBody Garbage garbage,@PathVariable String id){
        boolean exist = garbageService.isExist(id);
        if (exist){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject(200,"Updated successfully",garbageService.update(garbage))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponeObject(200,"Id does not exist","")
        );
    }
    @DeleteMapping("/api/garbage/{id}")
    public ResponseEntity<ResponeObject> delete(@PathVariable String id){
        boolean exist = garbageService.isExist(id);
        if(exist){
            garbageService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject(200,"Deleted successfully","")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponeObject(500,"Deleted failed",id)
        );


    }
}
