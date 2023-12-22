package com.example.userservicetestfinalapplication.exceptions;

public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String message){
        super(message);
    }
}
