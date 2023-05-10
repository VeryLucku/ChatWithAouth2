package com.edu.chatapi.CustomExceptions;

public class InvalidActionException extends RuntimeException{
    public InvalidActionException(String message) {
        super(message);
    }
}
