package com.travellog.travellog.exceptions;

public class TravelLogException {
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {
            super("Travel Log not found!");
        }

        public NotFoundException(String message) {
            super(message);
        }
    }
}
