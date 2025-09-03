
package com.ucms.ucmsapi.common;

import com.ucms.ucmsapi.user.User;
import com.ucms.ucmsapi.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    private final UserRepository users;
    public SecurityUtils(UserRepository users){ this.users = users; }

    public User currentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) throw new RuntimeException("Unauthenticated");
        return users.findByUsername(auth.getName()).orElseThrow();
    }

    public boolean hasRole(User u, String role){
        return u.getRoles().stream().anyMatch(r -> r.name().equals(role));
    }
}
