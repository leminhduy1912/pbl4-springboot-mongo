package com.pbl4.garbageclassification;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Garbage Classifition",version = "1.0.0",description = "test"))
public class GarbageClassificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GarbageClassificationApplication.class, args);
		 }
}
