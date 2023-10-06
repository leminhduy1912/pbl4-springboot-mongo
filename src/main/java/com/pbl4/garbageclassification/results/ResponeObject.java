package com.pbl4.garbageclassification.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponeObject {
    private int status;
    private String message;
    private Object data;

}
