package com.pbl4.garbageclassification.collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "kindOfGarbage")
public class KinfOfGarbage {
    private Long kindOfGarbage_id;
    private String kindOfGabage_name;
}
