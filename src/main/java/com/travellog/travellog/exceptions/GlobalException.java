package com.travellog.travellog.exceptions;

import com.travellog.travellog.helpers.ResponseHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();

        BindingResult bindingResult = ex.getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();
        for (ObjectError globalError : globalErrors) {
            errors.put(globalError.getObjectName(), globalError.getDefaultMessage());
        }

        ResponseHelper.CustomResponse<Map<String, String>> response = new ResponseHelper.CustomResponse<>(false, "Validation failed", errors);

        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
    }
}
