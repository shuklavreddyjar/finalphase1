package com.kirana.finalphase1.controller;

import com.kirana.finalphase1.document.UserDocument;
import com.kirana.finalphase1.dto.LoginRequestDTO;
import com.kirana.finalphase1.dto.SignupRequestDTO;
import com.kirana.finalphase1.exception.InvalidCredentialsException;
import com.kirana.finalphase1.repository.UserRepository;
import com.kirana.finalphase1.security.JwtUtil;
import com.kirana.finalphase1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthController(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil,
                          UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * PUBLIC USER SIGNUP
     */
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(
            @Valid @RequestBody SignupRequestDTO request) {

        userService.signupUser(
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }

    /**
     * LOGIN (USER / ADMIN)
     */
    @PostMapping("/login")
    public Map<String, String> login(
            @Valid @RequestBody LoginRequestDTO request) {

        UserDocument user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid username or password")
                );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // 🔥 JWT now carries userId (Mongo ObjectId), NOT email
        String token = jwtUtil.generateToken(
                user.getId().toHexString(),
                user.getRole()
        );

        return Map.of("token", token);
    }
}
