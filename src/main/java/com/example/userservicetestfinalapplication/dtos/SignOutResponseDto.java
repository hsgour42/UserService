package com.example.userservicetestfinalapplication.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SignOutResponseDto {
    private UUID id;
    private String token;
}
