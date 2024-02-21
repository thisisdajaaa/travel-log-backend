package com.travellog.travellog.exceptions;

public class EntityException {

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String entity) {
            super(entity+ " not found!");
        }
    }

    public static class AlreadyExistsException extends RuntimeException {
        public AlreadyExistsException(String entity) {
            super(entity + " already exists!");
        }
    }
}
