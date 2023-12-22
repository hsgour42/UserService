package com.example.userservicetestfinalapplication.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ExceptionDto {
    private String message;
    private HttpStatus status;

}
