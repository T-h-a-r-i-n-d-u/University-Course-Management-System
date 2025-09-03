
package com.ucms.ucmsapi.admin;

import com.ucms.ucmsapi.user.Role;
import com.ucms.ucmsapi.user.dto.UserDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService service;

    public AdminUserController(AdminUserService service) {
        this.service = service;
    }

    // ---- pending approvals
    @GetMapping("/pending")
    public List<UserDto> pending() { return service.pending(); }

    @PostMapping("/{id}/approve")
    public UserDto approve(@PathVariable Long id) { return service.approve(id); }

    @PostMapping("/{id}/reject")
    public UserDto reject(@PathVariable Long id) { return service.reject(id); }

    // ---- lecturers
    @GetMapping("/lecturers")
    public List<UserDto> lecturers() { return service.lecturers(); }

    @PostMapping("/lecturers")
    public UserDto createLecturer(@RequestBody Map<String,Object> b) {
        return service.createLecturer(
                (String)b.get("username"),
                (String)b.get("fullName"),
                (String)b.getOrDefault("email",""),
                (String)b.get("password")
        );
    }

    // ---- everyone
    @GetMapping
    public List<UserDto> all() { return service.all(); }

    @PostMapping("/{id}/roles")
    public UserDto setRoles(@PathVariable Long id, @RequestBody Map<String,Object> b) {
        @SuppressWarnings("unchecked")
        List<String> list = (List<String>) b.getOrDefault("roles", List.of());
        Set<Role> roles = new HashSet<>();
        for (String s : list) roles.add(Role.valueOf(s));
        return service.setRoles(id, roles);
    }

    @PostMapping("/{id}/approval")
    public UserDto toggleApproval(@PathVariable Long id, @RequestParam boolean approved) {
        return service.toggleApproval(id, approved);
    }
}
