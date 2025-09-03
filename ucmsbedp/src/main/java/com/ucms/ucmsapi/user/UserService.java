package com.ucms.ucmsapi.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User registerStudent(String username, String email, String rawPassword, String fullName) {
        if (repo.existsByUsername(username)) throw new RuntimeException("Username exists");
        if (repo.existsByEmail(email)) throw new RuntimeException("Email exists");
        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setFullName(fullName);
        u.setPassword(encoder.encode(rawPassword));
        u.setStatus(UserStatus.PENDING);
        u.setRoles(new HashSet<>(Set.of(Role.STUDENT)));
        return repo.save(u);
    }

    public List<User> pendingUsers() {
        return repo.findAll().stream().filter(u -> u.getStatus() == UserStatus.PENDING).collect(Collectors.toList());
    }

    public User approve(Long id) {
        User u = repo.findById(id).orElseThrow();
        u.setStatus(UserStatus.ACTIVE);
        return repo.save(u);
    }

    public User reject(Long id) {
        User u = repo.findById(id).orElseThrow();
        u.setStatus(UserStatus.REJECTED);
        return repo.save(u);
    }

    public User assignRoles(Long id, Set<Role> roles) {
        User u = repo.findById(id).orElseThrow();
        u.setRoles(new HashSet<>(roles));
        return repo.save(u);
    }
}