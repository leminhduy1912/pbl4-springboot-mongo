package com.pbl4.garbageclassification.controllers;


import com.pbl4.garbageclassification.collections.Garbage;
import com.pbl4.garbageclassification.results.ResponeObject;
import com.pbl4.garbageclassification.services.GetResultAIServer;
import com.pbl4.garbageclassification.services.IGarbageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RestController
@Controller
public class GarbageController {
    @Autowired
    private IGarbageService garbageService;
    @Autowired
    private GetResultAIServer getResultAIServer;




    @MessageMapping("/getAllGarbages")
    @SendTo("/topic/getAllGarbages")
    public ResponseEntity<ResponeObject> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponeObject(200,"Querry successfully",garbageService.findAll())
        );
    }

    @MessageMapping("/getGarbageById")
    @SendTo("/topic/getGarbageById")
    public ResponseEntity<ResponeObject> getAll(@PathVariable String id){
        System.out.print("id"+id);
        boolean exist = garbageService.isExist(id);
        if(exist){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject(200,"Querry by id successfully",garbageService.findById(id))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponeObject(500,"Id does not exist","")
        );

    }

    @MessageMapping("/addGarbage")
    @SendTo("/topic/addGarbage")
    public ResponseEntity<ResponeObject> save(@RequestParam("kindOfGarbage") String kindOfGarbage,
                                              @RequestParam("numOfBin") String numOfBin,
                                              @RequestParam("images")MultipartFile[] files){

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponeObject(200,"Created successfully",garbageService.save(kindOfGarbage, numOfBin, files))
        );
    }
    @PostMapping("/getResult")
    public String getResult(@RequestParam("img") MultipartFile img) throws IOException {
        System.out.println("json data" + img);
        String res = getResultAIServer.callExternalApiWithFormData(img);
        return null;
//        return getResultAIServer.callExternalApiWithJson(img);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponeObject(200,
//                        "Get Result successfully",
//                        getResultAIServer.callExternalApiWithJson(img))
//        );
//        return null;
    }

//    @MessageMapping("/updateGarbage")
//    @SendTo("/topic/updateGarbage")
//    public ResponseEntity<ResponeObject> update(@RequestParam("kindOfGarbage") String kindOfGarbage,
//                                                @RequestParam("garbageId") String garbageId,
//                                                @RequestParam("numOfBin") String numOfBin,
//                                                @RequestParam("images")MultipartFile[] files){
//        boolean exist = garbageService.isExist(garbageId);
//        if (exist){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponeObject(200,"Updated successfully",garbageService.update(garbageId,kindOfGarbage,numOfBin,files)
//            )
//            );
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                new ResponeObject(500,"Id does not exist","")
//        );
//    }

        @MessageMapping("/updateGarbage")
    @SendTo("/topic/updateGarbage")
            public ResponseEntity<ResponeObject> update(@RequestBody Garbage garbage,@RequestParam MultipartFile [] files){
        boolean exist = garbageService.isExist(garbage.getGabargeId());
        if (exist){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject(200,"Updated successfully",garbageService.update(garbage,files)
            )
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponeObject(500,"Id does not exist","")
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
