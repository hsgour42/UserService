package com.example.userservicetestfinalapplication.controllers;

import com.example.userservicetestfinalapplication.dtos.ExceptionDto;
import com.example.userservicetestfinalapplication.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(
            UserNotFoundException userNotFoundException
    ){
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(userNotFoundException.getMessage())
                .build();
        return new ResponseEntity<>(exceptionDto , HttpStatus.NOT_FOUND);
    }



}
