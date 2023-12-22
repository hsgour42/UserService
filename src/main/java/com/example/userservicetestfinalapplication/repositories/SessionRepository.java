package com.example.userservicetestfinalapplication.repositories;

import com.example.userservicetestfinalapplication.models.Session;
import com.example.userservicetestfinalapplication.models.SessionStatus;
import com.example.userservicetestfinalapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session , UUID> {
    Optional<Session> findByUser_IdAndToken(UUID id ,String token);
    Optional<List<Session>> findByUserAndSessionStatus(User user , SessionStatus sessionStatus);





}
