package com.ucms.ucmsapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    @Autowired
    public DataInitializer(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Bean
    CommandLineRunner initAdmin() {
        return args -> {
            if (users.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@ucms.local");
                admin.setFullName("System Admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setStatus(UserStatus.ACTIVE);
                admin.setRoles(new HashSet<>(Set.of(Role.ADMIN)));
                users.save(admin);
            }
        };
    }
}