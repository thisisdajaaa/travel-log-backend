package com.travellog.travellog.exceptions;

public class CountryException {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {
            super("Country not found!");
        }

        public NotFoundException(String message) {
            super(message);
        }
    }
}
