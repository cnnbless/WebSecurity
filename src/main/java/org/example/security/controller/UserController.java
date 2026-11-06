package org.example.security.controller;

/*
  @author   Tony
  @project   security
  @class  UserController
  @version  1.0.0 
  @since 30.09.2026 - 21.16
*/
import lombok.RequiredArgsConstructor;
import org.example.security.model.User;
import org.example.security.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public List<User> getAllUsers() { return userService.getAll(); }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN')")
    public User getUserById(@PathVariable String id) { return userService.getById(id); }

    @PostMapping
    @PreAuthorize("hasRole('SUPERADMIN')")
    public User createUser(@RequestBody User user) { return userService.create(user); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public User updateUser(@PathVariable String id, @RequestBody User user) { return userService.update(id, user); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public void deleteUser(@PathVariable String id) { userService.delete(id); }
}
