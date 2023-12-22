package com.example.userservicetestfinalapplication.dtos;

import com.example.userservicetestfinalapplication.models.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;

    public User convertUserDtoToUser(){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        return user;
    }
}
