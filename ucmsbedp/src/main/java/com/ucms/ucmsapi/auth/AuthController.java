package com.ucms.ucmsapi.auth;

import com.ucms.ucmsapi.auth.dto.AuthResponse;
import com.ucms.ucmsapi.auth.dto.LoginRequest;
import com.ucms.ucmsapi.auth.dto.RegisterRequest;
import com.ucms.ucmsapi.user.User;
import com.ucms.ucmsapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UserService users;

    @Autowired
    public AuthController(AuthenticationManager authManager, JwtService jwt, UserService users) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.users = users;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r) {
        users.registerStudent(r.username(), r.email(), r.password(), r.fullName());
        return ResponseEntity.ok("Registered. Await admin approval.");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest r) {
        Authentication a = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(r.username(), r.password())
        );
        User u = (User) a.getPrincipal();
        String token = jwt.generateToken(u);

        Set<String> roleNames = u.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        return ResponseEntity.ok(new AuthResponse(
                token, u.getId(), u.getUsername(), u.getFullName(),
                roleNames, u.getStatus().name()
        ));
    }

    @GetMapping("/pending")
    public Object pending() { return users.pendingUsers(); }

    @PatchMapping("/approve/{id}")
    public Object approve(@PathVariable Long id) { return users.approve(id); }

    @PatchMapping("/reject/{id}")
    public Object reject(@PathVariable Long id) { return users.reject(id); }

    @PatchMapping("/roles/{id}")
    public Object roles(@PathVariable Long id, @RequestBody Set<com.ucms.ucmsapi.user.Role> roles) {
        return users.assignRoles(id, roles);
    }
}