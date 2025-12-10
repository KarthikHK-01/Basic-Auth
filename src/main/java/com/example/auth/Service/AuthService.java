package com.example.auth.Service;

import com.example.auth.AuthDTO.LoginRequest;
import com.example.auth.AuthDTO.RegisterRequest;
import com.example.auth.Entity.User;
import com.example.auth.JWT.JWTService;
import com.example.auth.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public final UserRepo userRepo;
    public final PasswordEncoder passwordEncoder;
    public final JWTService jwtService;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder, JWTService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
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

    public String login(LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail()).orElse(null);

        if(user == null) {
            return null;
        }
        
        boolean correctPassword = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        );

        if(!correctPassword) {
            return null;
        }

        return jwtService.generateToken(loginRequest.getEmail());
    }
}
