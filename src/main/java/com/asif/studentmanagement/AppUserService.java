package com.asif.studentmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser register(AppUser user){

    user.setPassword(
        passwordEncoder.encode(user.getPassword())
    );

    return repository.save(user);
}
}