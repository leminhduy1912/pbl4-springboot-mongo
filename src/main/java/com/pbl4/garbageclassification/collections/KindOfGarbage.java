package com.pbl4.garbageclassification.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "kindOfGarbage")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KindOfGarbage {
    @Id
    private String kindOfGarbage_id;
    private String kindOfGabage_name;
    private int bin_num;
}
