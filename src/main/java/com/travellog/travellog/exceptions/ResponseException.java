package com.travellog.travellog.exceptions;

import com.travellog.travellog.helpers.ResponseHelper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ResponseException extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            UserException.NotFoundException.class,
            CountryException.NotFoundException.class,
            TravelLogException.NotFoundException.class
    })
    public final ResponseEntity<ResponseHelper.CustomResponse<Object>> handleNotFoundException(RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("RuntimeException", ex.getMessage());

        ResponseHelper.CustomResponse<Object> response = new ResponseHelper.CustomResponse<>(false,
                "An error occurred!", null, errors);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            UserException.AlreadyExistsException.class,
    })
    public final ResponseEntity<ResponseHelper.CustomResponse<Object>> handleAlreadyExistsException(
            RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("RuntimeException", ex.getMessage());

        ResponseHelper.CustomResponse<Object> response = new ResponseHelper.CustomResponse<>(false,
                "An error occurred!", null, errors);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseHelper.CustomResponse<Object>> handleIOException(IOException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("IOException", ex.getMessage());

        ResponseHelper.CustomResponse<Object> response = new ResponseHelper.CustomResponse<>(false,
                "An error occurred!", null, errors);

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Malformed JSON request", ex.getMessage());

        ResponseHelper.CustomResponse<Object> apiError = new ResponseHelper.CustomResponse<>(false,
                "An error occurred!", null, errors);

        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

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

        ResponseHelper.CustomResponse<Map<String, String>> response = new ResponseHelper.CustomResponse<>(false,
                "Validation failed!", null, errors);

        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);
    }
}
