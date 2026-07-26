package com.asif.studentmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AppUserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public AppUser register(@RequestBody AppUser user) {
        return service.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()));

        return jwtUtil.generateToken(request.getUsername());
    }
}