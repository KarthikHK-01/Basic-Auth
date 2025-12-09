package com.example.auth.Controller;

import com.example.auth.AuthDTO.LoginRequest;
import com.example.auth.AuthDTO.RegisterRequest;
import com.example.auth.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try{
            authService.register(request);
            return ResponseEntity.status(HttpStatus.OK).body("User Registered Successfully");
        }catch (RuntimeException e){
//            throw new Exception(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        boolean success = authService.login(request);

        if(success) {
            return ResponseEntity.status(HttpStatus.OK).body("Login Success");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are unauthorized");
        }
    }
}
