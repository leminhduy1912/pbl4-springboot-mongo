package com.pbl4.garbageclassification.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "garbage")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Garbage {
    @Id
    private String gabargeId;
    private String image;
    private String kindOfGarbage;
    private String classOfGarbage;
    private LocalDateTime timeCreate;


}
