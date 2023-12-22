package com.example.userservicetestfinalapplication.repositories;

import com.example.userservicetestfinalapplication.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session , UUID> {
    Optional<Session> findByUser_IdAndToken(UUID id , String token);
}
