package com.example.auth.Service;

import com.example.auth.AuthDTO.LoginRequest;
import com.example.auth.AuthDTO.RegisterRequest;
import com.example.auth.Entity.User;
import com.example.auth.Repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public final UserRepo userRepo;
    public final PasswordEncoder passwordEncoder;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest registerRequest) {

        if(userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User user = new User(
                registerRequest.getName(),
                registerRequest.getEmail(),
                hashedPassword
        );

        userRepo.save(user);
    }

    public boolean login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElse(null);

        if(user == null) {
            return false;
        }

        return passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
    }
}
