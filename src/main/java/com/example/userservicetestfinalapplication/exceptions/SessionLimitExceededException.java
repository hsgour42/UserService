package com.example.userservicetestfinalapplication.exceptions;

public class SessionLimitExceededException extends Exception{
    public SessionLimitExceededException(String message){
        super(message);
    }
}
