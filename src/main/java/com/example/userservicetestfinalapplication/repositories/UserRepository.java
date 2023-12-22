package com.example.userservicetestfinalapplication.repositories;

import com.example.userservicetestfinalapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findUserById(UUID id);
    Optional<User> findByEmail(String email);
}
