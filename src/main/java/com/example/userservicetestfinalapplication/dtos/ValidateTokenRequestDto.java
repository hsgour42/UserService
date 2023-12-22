package com.example.userservicetestfinalapplication.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ValidateTokenRequestDto {
    private UUID userID;
    private String token;
}
