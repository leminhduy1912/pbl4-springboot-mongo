package com.pbl4.garbageclassification.controllers.api;

import com.pbl4.garbageclassification.results.ResponeObject;
import com.pbl4.garbageclassification.services.IGarbageService;
import com.pbl4.garbageclassification.services.IQueueProcess;
import com.pbl4.garbageclassification.services.impl.GarbageService;
import com.pbl4.garbageclassification.services.impl.GetResultAIServer;
import com.pbl4.garbageclassification.services.impl.QueueProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class Garbage {
    private IGarbageService garbageService;
    private IQueueProcess queueProcess;
    private GetResultAIServer getResultAIServer;
    public Garbage(GarbageService garbageService, QueueProcess queueProcess, GetResultAIServer getResultAIServer) {
        this.garbageService = garbageService;
        this.queueProcess = queueProcess;
        this.getResultAIServer = getResultAIServer;
    }
    @GetMapping("api/v1/garbage/{id}")
    public ResponseEntity<ResponeObject> getById(@PathVariable String id){
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
    @GetMapping("api/v1/garbage")
    public ResponseEntity<ResponeObject> getAll(){


            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject(200,"Querry by id successfully",garbageService.findAll())
            );



    }
    @PutMapping("api/v1/garbage")

    public ResponseEntity<ResponeObject> update(@RequestParam("kindOfGarbage") String kindOfGarbage,
                                                @RequestParam("garbageId") String garbageId,
                                                @RequestParam("classOfGarbage") String classOfGarbage,
                                                @RequestParam("image") MultipartFile file){
        boolean exist = garbageService.isExist(garbageId);
        if (exist){
            com.pbl4.garbageclassification.collections.Garbage garbage = new com.pbl4.garbageclassification.collections.Garbage();
            garbage.setClassOfGarbage(classOfGarbage);
            garbage.setKindOfGarbage(kindOfGarbage);
            garbage.setGabargeId(garbageId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject(200,"Updated successfully",garbageService.update(garbage,file)
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponeObject(500,"Id does not exist","")
        );
    }
    @DeleteMapping("/api/v1/garbage/{id}")
    public ResponseEntity<ResponeObject> delete(@PathVariable String id) {
        boolean exist = garbageService.isExist(id);
        if (exist) {
            garbageService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject(200, "Deleted successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponeObject(500, "Deleted failed", id)
        );
    }
    @PostMapping("/api/v1/garbage")
    public ResponseEntity<String> save(@RequestParam("kindOfGarbage") String kindOfGarbage,
                                       @RequestParam("classOfGarbage") String classOfGarbage,
                                       @RequestParam("img") String fileName) {
        System.out.println("img" + fileName);
        System.out.println("kindOfGarbage" + kindOfGarbage);
        System.out.println("classOfGarbage" + classOfGarbage);
        String idGarbage = garbageService.save(kindOfGarbage, fileName.trim(), classOfGarbage);
        if (idGarbage != null) {
            return ResponseEntity.status(HttpStatus.OK).body(idGarbage);
        } else {
            return null;
        }
    }
    @PostMapping("/img")
    public ResponseEntity<String> receiveImage(@RequestBody MultipartFile imageFile) {
        System.out.println("image receive" + imageFile);
        Integer numOfFile = queueProcess.countImage();
        System.out.println("num of image" + numOfFile);
        if (numOfFile != 0) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Proccessing,please wait and try again .....");
        } else {
            String fileName = queueProcess.storeFile(imageFile);
            //String storeFile = imageService.storeFile(imageFile);
            // Return a success response
            return ResponseEntity.status(HttpStatus.OK).body("Image successfully received and saved.");
        }

    }
    @GetMapping("api/v1/result")
    public ResponseEntity<String> getBytesOfImage(@RequestParam String imgName) throws IOException {

        File imageFile = new File("C:\\Users\\minhd\\OneDrive\\Desktop\\pbl4\\garbage-classification\\uploads\\" + imgName);
        byte[] imageData = Files.readAllBytes(imageFile.toPath());//
        String result = getResultAIServer.callExternalApiWithFormData(imageData);
        System.out.println("result" + result);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }

    }
}
