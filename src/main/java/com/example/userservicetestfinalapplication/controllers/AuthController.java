package com.example.userservicetestfinalapplication.controllers;

import com.example.userservicetestfinalapplication.dtos.*;
import com.example.userservicetestfinalapplication.models.SessionStatus;
import com.example.userservicetestfinalapplication.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(
            @RequestBody SignUpRequestDto request
    ){
        UserResponseDto responseDto = authService.signUp(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );
        return new ResponseEntity<>(responseDto , HttpStatus.CREATED);
    };
    @GetMapping("/signin")
    public ResponseEntity<UserResponseDto> signIn(
            @RequestBody SignInRequestDto request
    )throws Exception{
        return authService.signIn(
                request.getEmail(),
                request.getPassword()
        );
    };
    @PostMapping("/signout")
    public ResponseEntity<Void> signOut(
            @RequestBody SignOutResponseDto request
    ){
         authService.signOut(
                    request.getUserId(),
                    request.getToken()
        );
        return null;
    };
    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(
            @RequestBody ValidateTokenRequestDto request
    ){
        SessionStatus sessionStatus = authService.validateToken(
                request.getUserID(),
                request.getToken()
        );
        return new ResponseEntity<>(sessionStatus , HttpStatus.OK);
    };


}
