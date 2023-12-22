package com.example.userservicetestfinalapplication.services;

import com.example.userservicetestfinalapplication.dtos.UserResponseDto;
import com.example.userservicetestfinalapplication.exceptions.UserNotFoundException;
import com.example.userservicetestfinalapplication.models.Role;
import com.example.userservicetestfinalapplication.models.User;
import com.example.userservicetestfinalapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
@Qualifier("USER_SERVICE_DB")
public class UserServiceDb implements UserService{
    private final  UserRepository userRepository;
    @Autowired
    public UserServiceDb(
            UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }
    @Override
    public UserResponseDto getUserDetails(UUID id) throws Exception {
        Optional<User> optionalUser  = userRepository.findUserById(id);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found with given id : " + id);
        }
        return UserResponseDto.convertModelToDto(optionalUser.get());
    }

    @Override
    public UserResponseDto setUserRoles(UUID id, Set<Role> roles) throws Exception {
        Optional<User> optionalUser  = userRepository.findUserById(id);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User not found with given id : " + id);
        }

        User currentUser = optionalUser.get();
        currentUser.setRoles(Set.copyOf(roles));

        User savedUser = userRepository.save(currentUser);

        return UserResponseDto.convertModelToDto(savedUser);
    }
}
