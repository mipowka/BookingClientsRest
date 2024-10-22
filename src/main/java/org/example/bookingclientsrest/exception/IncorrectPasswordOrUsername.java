package org.example.bookingclientsrest.exception;

public class IncorrectPasswordOrUsername extends RuntimeException{
    public IncorrectPasswordOrUsername(String message) {
        super(message);
    }
}
