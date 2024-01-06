package com.example.userservicetestfinalapplication;

import com.example.userservicetestfinalapplication.dtos.UserResponseDto;
import com.example.userservicetestfinalapplication.exceptions.RoleAlreadyAvailableException;
import com.example.userservicetestfinalapplication.exceptions.UserNotFoundException;
import com.example.userservicetestfinalapplication.models.Role;
import com.example.userservicetestfinalapplication.models.User;
import com.example.userservicetestfinalapplication.repositories.RoleRepository;
import com.example.userservicetestfinalapplication.repositories.UserRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@SpringBootTest
public class UserTest {
    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Inject
    private UserRepository userRepository;
    @Inject
    private RoleRepository roleRepository;
    @Test
    public void signUpTest() {
        String name ="Himanshu";
        String email = "himanshu@gmail.com";
        String password ="password";

        User newUser = User
                .builder()
                .name(name)
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .build();

        User savedUser = userRepository.save(newUser);

    }

    @Test
    public void createRoleTest(){
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("Admin"));
        roles.add(new Role("Student"));
        roles.add(new Role("Mentor"));
        roles.add(new Role("Instructor"));
        roles.add(new Role("TA"));
        roleRepository.saveAll(roles);
    }

    @Test
    public void  setUserRoles()  {

        Optional<Role> admin = roleRepository.findByRole("Admin");
        Optional<Role> mentor = roleRepository.findByRole("Mentor");

        Set<Role> roles = new HashSet<>();
        roles.add(admin.get());
        roles.add(mentor.get());

        UUID id = UUID.fromString("55865e3c-a671-4547-85c7-bf5d62d83fe5");

        Optional<User> optionalUser  = userRepository.findUserById(id);
        User currentUser = optionalUser.get();
        currentUser.setRoles(Set.copyOf(roles));
        User savedUser = userRepository.save(currentUser);
    }




}
