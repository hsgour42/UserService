package com.example.userservicetestfinalapplication.services;

import com.example.userservicetestfinalapplication.dtos.JwsTokenPayload;
import com.example.userservicetestfinalapplication.dtos.UserResponseDto;
import com.example.userservicetestfinalapplication.exceptions.InvalidPasswordException;
import com.example.userservicetestfinalapplication.exceptions.SessionLimitExceededException;
import com.example.userservicetestfinalapplication.exceptions.UserNotFoundException;
import com.example.userservicetestfinalapplication.models.Role;
import com.example.userservicetestfinalapplication.models.Session;
import com.example.userservicetestfinalapplication.models.SessionStatus;
import com.example.userservicetestfinalapplication.models.User;
import com.example.userservicetestfinalapplication.repositories.SessionRepository;
import com.example.userservicetestfinalapplication.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        //find user
        Optional<User> optionalEmailUser = userRepository.findByEmail(email);
        if(optionalEmailUser.isEmpty()){
            throw new UserNotFoundException("User is not available with with email id : " + email);
        }
        User currentUser = optionalEmailUser.get();

        if(!bCryptPasswordEncoder.matches(password , currentUser.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }

        //find all active session
        Optional<List<Session>> optionalSessions =
                sessionRepository.findByUserAndSessionStatus(currentUser , SessionStatus.ACTIVE);

        if(optionalSessions.isPresent() && optionalSessions.get().size() >= 2){
            throw new SessionLimitExceededException("Session Limit has been exceeded");
        }

        //Generate Token
        //String token = RandomStringUtils.randomAlphanumeric(30);
         JwsTokenPayload jwsTokenPayload = JwsTokenPayload.builder()
                .roles(currentUser.getRoles())
                .email(currentUser.getEmail())
                .createdAt(new Date())
                .expiredAt(DateUtils.addDays(new Date() , 2))
                .build();

        String jwsToken = jwsTokenGenerator(jwsTokenPayload);

        //Generate new session
        Session currentUserSession = new Session();
        currentUserSession.setSessionStatus(SessionStatus.ACTIVE);
        currentUserSession.setUser(currentUser);
        currentUserSession.setToken(jwsToken);
        sessionRepository.save(currentUserSession);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + jwsToken);

        return new ResponseEntity<>(UserResponseDto.convertModelToDto(currentUser), headers, HttpStatus.OK);
    }

    private String jwsTokenGenerator(JwsTokenPayload jwsTokenPayload) {
        // Create a test key suitable for the desired HMAC-SHA algorithm:
        MacAlgorithm alg = Jwts.SIG.HS256; //or HS384 or HS256
        SecretKey key = alg.key().build();

        Map<String , Object> jwtPayload = new HashMap<>();
        jwtPayload.put("email" , jwsTokenPayload.getEmail());
        jwtPayload.put("role" , jwsTokenPayload.getRoles());
        jwtPayload.put("createdAt" , jwsTokenPayload.getCreatedAt());
        jwtPayload.put("expiredAt" , jwsTokenPayload.getExpiredAt());
        return Jwts
                .builder()
                .claims(jwtPayload)
                .signWith(key , alg)
                .compact();
    }

    ;
    public void signOut(
            UUID userId,
            String token
    ){
        Optional<Session> sessionOptional = sessionRepository.findByUser_IdAndToken(userId , token);
        if (sessionOptional.isEmpty()) {
            return;
        }
        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);

    };
    public SessionStatus validateToken(
            UUID userId,
            String token
    ){
        Optional<Session> userSessionOptional = sessionRepository.findByUser_IdAndToken(userId , token);

        if(userSessionOptional.isEmpty()){
            return null;
        }

        Session session = userSessionOptional.get();
        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return SessionStatus.ENDED;
        }

        Date currentDate = new Date();
        if(session.getExpiringAt().before(currentDate)){
            return SessionStatus.ENDED;
        }

        //JWT Decoding.
        Jws<Claims> jwsClaims = Jwts.parser().build().parseSignedClaims(token);

        String email = (String) jwsClaims.getPayload().get("email");
        List<Role> roles = (List<Role>) jwsClaims.getPayload().get("role");

//        if(rejectedEmailsList.contains(email)){
//
//        }

        return SessionStatus.ACTIVE;
    };
}
