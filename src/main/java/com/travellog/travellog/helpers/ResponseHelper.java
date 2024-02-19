package com.travellog.travellog.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class ResponseHelper {

    @Data
    public static class CustomResponse<T> {
        private Boolean success;
        private String message;
        private T data;
        private Map<String, String> errors;

        public CustomResponse(Boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
            this.errors = null;
        }

        public CustomResponse(Boolean success, String message, T data, Map<String, String> errors) {
            this.success = success;
            this.message = message;
            this.data = data;
            this.errors = errors;
        }
    }

    public static void respondWithUnauthorizedError(HttpServletResponse response, String message) throws IOException {
        Map<String, String> errors = Collections.singletonMap("IOException", message);

        CustomResponse<Object> responseBody = new CustomResponse<>(false, "An error occurred!", null, errors);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }

}
