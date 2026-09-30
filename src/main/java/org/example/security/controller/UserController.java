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
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() { return userService.getAll(); }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) { return userService.getById(id); }

    @PostMapping
    public User createUser(@RequestBody User user) { return userService.create(user); }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) { return userService.update(id, user); }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) { userService.delete(id); }
}