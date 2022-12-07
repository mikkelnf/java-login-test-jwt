package com.enigma.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super("Unathorized");
    }
}
