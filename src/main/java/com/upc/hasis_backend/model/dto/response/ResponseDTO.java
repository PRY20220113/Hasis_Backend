package com.upc.hasis_backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {

    private int httpCode = HttpStatus.OK.value();
    private int errorCode = 0;
    private String errorMessage = "";
    private T data;
}