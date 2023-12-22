package com.example.userservicetestfinalapplication.services;

import com.example.userservicetestfinalapplication.dtos.UserResponseDto;
import com.example.userservicetestfinalapplication.exceptions.InvalidPasswordException;
import com.example.userservicetestfinalapplication.exceptions.UserNotFoundException;
import com.example.userservicetestfinalapplication.models.Session;
import com.example.userservicetestfinalapplication.models.SessionStatus;
import com.example.userservicetestfinalapplication.models.User;
import com.example.userservicetestfinalapplication.repositories.SessionRepository;
import com.example.userservicetestfinalapplication.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthService(
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserRepository userRepository,
            SessionRepository sessionRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public UserResponseDto signUp(
            String name,
            String email,
            String password
    ) {

        User newUser = User
                .builder()
                .name(name)
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .build();

        User savedUser = userRepository.save(newUser);
        return UserResponseDto.convertModelToDto(savedUser);
    }

    public ResponseEntity<UserResponseDto> signIn(
            String email,
            String password
    ) throws Exception{
        Optional<User> optionalEmailUser = userRepository.findByEmail(email);
        if(optionalEmailUser.isEmpty()){
            throw new UserNotFoundException("User is not available with with email id : " + email);
        }
        User currentUser = optionalEmailUser.get();
        if(!bCryptPasswordEncoder.matches(password , currentUser.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }
        //Generate Token for first time
        String token = RandomStringUtils.randomAlphanumeric(30);

        Session currentUserSession = new Session();
        currentUserSession.setSessionStatus(SessionStatus.ACTIVE);
        currentUserSession.setUser(currentUser);
        currentUserSession.setToken(token);
        sessionRepository.save(currentUserSession);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        return new ResponseEntity<>(UserResponseDto.convertModelToDto(currentUser), headers, HttpStatus.OK);
    };
    public void signOut(
            UUID id,
            String token
    ){
        Optional<Session> sessionOptional = sessionRepository.findByUser_IdAndToken(id, token);

        if (sessionOptional.isEmpty()) {
            return;
        }
        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        session.setToken(null);
        sessionRepository.save(session);

    };
    public SessionStatus validateToken(
            UUID id,
            String token
    ){
        Optional<Session> userSession = sessionRepository.findByUser_IdAndToken(id , token);
        if(userSession.isEmpty()){
            return null;
        }
        return SessionStatus.ACTIVE;
    };
}
