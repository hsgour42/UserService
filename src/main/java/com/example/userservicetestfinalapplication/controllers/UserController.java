package com.example.userservicetestfinalapplication.controllers;

import com.example.userservicetestfinalapplication.dtos.RolesRequestDto;
import com.example.userservicetestfinalapplication.dtos.UserResponseDto;
import com.example.userservicetestfinalapplication.dtos.UserRoleRequestDto;
import com.example.userservicetestfinalapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(
            @Qualifier("USER_SERVICE_DB") UserService userService
    ){
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserDetails(
            @PathVariable("id") UUID  id
    ) throws Exception {
         UserResponseDto responseDto = userService.getUserDetails(id);


         return new  ResponseEntity<>(responseDto , HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserResponseDto> setUserRoles(
            @PathVariable("id") UUID  id ,
            @RequestBody UserRoleRequestDto userRoleRequestDto
    ) throws Exception {
        UserResponseDto responseDto = userService.setUserRoles(id , userRoleRequestDto.getRoles());
        return new ResponseEntity<>(responseDto , HttpStatus.OK);
    }
}
