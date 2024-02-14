package com.travellog.travellog.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> response = new HashMap<String, Object>();
        Map<String, String> validationErrors = new HashMap<String, String>();

        BindingResult bindingResult = ex.getBindingResult();

        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());

        }

        List<ObjectError> globalErrors = bindingResult.getGlobalErrors();
        for (ObjectError globalError: globalErrors) {
            validationErrors.put(globalError.getObjectName(), globalError.getDefaultMessage());
        }

        response.put("errors", validationErrors);
        return new ResponseEntity<Object>(response, headers, status);
    }
}

