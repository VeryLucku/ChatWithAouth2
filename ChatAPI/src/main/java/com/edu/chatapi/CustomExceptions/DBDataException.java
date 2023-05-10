package com.edu.chatapi.CustomExceptions;

public class DBDataException extends RuntimeException {
    public DBDataException(String message) {
        super(message);
    }
}
