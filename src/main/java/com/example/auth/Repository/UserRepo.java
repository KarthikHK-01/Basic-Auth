package com.example.auth.Repository;

import com.example.auth.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByEmail(String name);
    Optional<User> findByEmail(String name);
}
