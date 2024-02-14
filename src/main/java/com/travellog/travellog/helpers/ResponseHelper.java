package com.travellog.travellog.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ResponseHelper<T> {
    private Boolean success;
    private String message;
    private T data;
}
