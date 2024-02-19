package com.travellog.travellog.exceptions;

public class UserException {

    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {
            super("User not found!");
        }

        public NotFoundException(String message) {
            super(message);
        }
    }

    public static class AlreadyExistsException extends RuntimeException {
        public AlreadyExistsException() {
            super("User already exists!");
        }

        public AlreadyExistsException(String message) {
            super(message);
        }
    }
}
