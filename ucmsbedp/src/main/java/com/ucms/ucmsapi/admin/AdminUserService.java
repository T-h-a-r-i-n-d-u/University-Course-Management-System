
package com.ucms.ucmsapi.admin;

import com.ucms.ucmsapi.user.Role;
import com.ucms.ucmsapi.user.User;
import com.ucms.ucmsapi.user.UserRepository;
import com.ucms.ucmsapi.user.UserStatus;
import com.ucms.ucmsapi.user.dto.UserDto;
import com.ucms.ucmsapi.user.dto.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.ucms.ucmsapi.user.UserStatus.ACTIVE;

@Service
@Transactional
public class AdminUserService {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    public AdminUserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    public List<UserDto> pending() {
        List<UserDto> pendingByApproval = users.findByApprovedFalse()
                .stream()
                .map(UserMapper::toDto)
                .toList();

        List<UserDto> pendingByStatus = users.findAll()
                .stream()
                .filter(u -> u.getStatus() == UserStatus.PENDING)
                .map(UserMapper::toDto)
                .toList();

        // Merge both results
        List<UserDto> result = new ArrayList<>(pendingByApproval);
        result.addAll(pendingByStatus);

        // If you want to remove duplicates (by id for example)
        return result.stream()
                .distinct()
                .toList();
    }


    public UserDto approve(Long id) {
        User u = users.findById(id).orElseThrow();
        u.setApproved(true);
        return UserMapper.toDto(users.save(u));
    }

    public UserDto reject(Long id) {
        // simple: delete unapproved account
        User u = users.findById(id).orElseThrow();
        if (!u.isApproved()) {
            users.delete(u);
            return UserMapper.toDto(u);
        }
        throw new IllegalStateException("Cannot reject an already approved user");
    }

    public List<UserDto> lecturers() {
        return users.findByRole(Role.LECTURER).stream().map(UserMapper::toDto).toList();
    }

    public UserDto createLecturer(String username, String fullName, String email, String rawPassword) {
        User u = new User();
        u.setUsername(username);
        u.setFullName(fullName);
        u.setEmail(email);
        u.setPassword(encoder.encode(rawPassword));
        u.setApproved(true);
        u.setStatus(ACTIVE);
        u.setRoles(new HashSet<>(Set.of(Role.LECTURER)));
        return UserMapper.toDto(users.save(u));
    }

    public List<UserDto> all() {
        return users.findAll().stream().map(UserMapper::toDto).toList();
    }

    public UserDto setRoles(Long id, Set<Role> roles) {
        User u = users.findById(id).orElseThrow();
        u.setRoles(roles);
        return UserMapper.toDto(users.save(u));
    }

    public UserDto toggleApproval(Long id, boolean approved) {
        User u = users.findById(id).orElseThrow();
        u.setApproved(approved);
        u.setStatus(ACTIVE);
        return UserMapper.toDto(users.save(u));
    }
}
