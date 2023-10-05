package com.pbl4.garbageclassification.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "image")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Image {
    @Id
    private String image_id;
    private String image_code;
}
