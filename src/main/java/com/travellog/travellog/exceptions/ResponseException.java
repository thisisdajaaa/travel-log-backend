package com.travellog.travellog.exceptions;

import com.travellog.travellog.helpers.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ResponseException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            UserException.NotFoundException.class,
    })
    public final ResponseEntity<ResponseHelper<Object>> handleNotFoundException(RuntimeException ex) {
        return buildResponse(null, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            UserException.AlreadyExistsException.class,
    })
    public final ResponseEntity<ResponseHelper<Object>> handleAlreadyExistsException(RuntimeException ex) {
        return buildResponse(null, ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ResponseHelper<Object>> handleIOException(IOException ex) {
        return buildResponse(null, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ResponseHelper<Object>> buildResponse(Object data, String message, HttpStatus status) {
        ResponseHelper<Object> response = new ResponseHelper<>(false, message, data);
        return new ResponseEntity<>(response, status);
    }

}
