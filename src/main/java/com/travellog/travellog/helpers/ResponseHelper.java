package com.travellog.travellog.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ResponseHelper {

    @Data
    @AllArgsConstructor
    public static class CustomResponse<T> {
        private Boolean success;
        private String message;
        private T data;
    }

    public static void respondWithUnauthorizedError(HttpServletResponse response, String message) throws IOException {
        CustomResponse<Object> responseBody = new CustomResponse<>(false, message, null);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }
}
